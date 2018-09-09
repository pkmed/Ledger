package Logic;

import GUI.Windows.BooksListWindow;
import wrap.JDBC_mysql_connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookListLogic {
    private static BooksListWindow booksListWindow;
    public static void main(String[] args){
        setUpDBConnection();
        showWindow();
    }
    static void showWindow(){
        booksListWindow = new BooksListWindow();
        booksListWindow.refreshList(getBooks());
    }
    private static void setUpDBConnection(){
        JDBC_mysql_connector.importJDBC();
        //TODO: implement creating other users and login for they
        JDBC_mysql_connector.establishConnection("localhost:3306", "ledger", "root", "666partywiththedevilbitch");
    }

    private static String[] getBooks() {
        try {
            ArrayList<String> books = new ArrayList<>();
            ResultSet rs = JDBC_mysql_connector.execQuery("SELECT * FROM books");
            while(rs.next()){
                books.add(rs.getString(2)+";"+rs.getString(3));
            }
            return books.toArray(new String[books.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addBook(String bookName) {
        try {
            JDBC_mysql_connector.execUpdate("INSERT INTO books (bookName, bookType) values ('"+bookName+"', 'income');");
            JDBC_mysql_connector.execUpdate("CREATE TABLE `ledger`.`"+bookName+"_book` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `entry` VARCHAR(45) NOT NULL,\n" +
                    "  `amount` INT(15) NOT NULL,\n" +
                    "  `entry_date` DATETIME NOT NULL,\n" +
                    "  PRIMARY KEY (`id`))"+
                    "DEFAULT CHARACTER SET = utf8;");

            booksListWindow.refreshList(getBooks());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteBook(){
        String bookName = booksListWindow.getSelectedBook().split(";")[0];
        try {
            JDBC_mysql_connector.execUpdate("DELETE FROM books WHERE bookName='"+bookName+"'");
            JDBC_mysql_connector.execUpdate("DROP TABLE "+bookName+"_book");
            booksListWindow.refreshList(getBooks());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void openBook() {
        String bookName = booksListWindow.getSelectedBook().split(";")[0];
        ArrayList<String> entries = new ArrayList<>();
        try {
            ResultSet rs = JDBC_mysql_connector.execQuery("SELECT * FROM "+bookName+"_book");
            while (rs.next()){
                entries.add(rs.getString(2)+";"+rs.getString(3)+";"+rs.getString(4));
            }
            booksListWindow.dispose();
            BookOverviewLogic.setOpenedBookName(bookName);
            BookOverviewLogic.showWindow(entries.toArray(new String[entries.size()]));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void exportBook(String bookName, String exportType){

    }
}
