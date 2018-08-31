package Logic;

import GUI.Windows.BookOverviewWindow;
import wrap.JDBC_mysql_connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookOverviewLogic {
    private static String openedBookName;
    private static BookOverviewWindow bookOverviewWindow;
    public static void showWindow(String[] entries){
        bookOverviewWindow = new BookOverviewWindow(openedBookName);
        bookOverviewWindow.refreshEntries(entries);
    }

    static void setOpenedBookName(String bookName) {
        openedBookName = bookName;
    }

    private static String[] getEntries(){
        ArrayList<String> entries = new ArrayList<>();
        try {
            ResultSet rs = JDBC_mysql_connector.execQuery("SELECT * FROM "+openedBookName+"_book ORDER BY entry_date");
            while (rs.next()){
                entries.add(rs.getString(2)+";"+rs.getString(3)+";"+rs.getString(4));
            }
            return entries.toArray(new String[entries.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getSelectedEntry(){
        return bookOverviewWindow.getSelectedEntry();
    }

    public static void addEntry(String[] values) {
        try {
            JDBC_mysql_connector.execUpdate("INSERT INTO "+openedBookName+"_book (entry, amount, entry_date) VALUES ('"+values[0]+"', '"+values[1]+"', '"+values[2]+"');");
            bookOverviewWindow.refreshEntries(getEntries());
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void removeEntry() {
        String selectedEntry = bookOverviewWindow.getSelectedEntry().split(";")[0];
        String selectedEntryAmount = bookOverviewWindow.getSelectedEntry().split(";")[1];
        String selectedEntryData = bookOverviewWindow.getSelectedEntry().split(";")[2];
        try {
            JDBC_mysql_connector.execUpdate("DELETE FROM "+openedBookName+"_book " +
                    "WHERE (entry_date='"+selectedEntryData+"' " +
                    "AND entry='"+selectedEntry+"' AND amount='"+selectedEntryAmount+"');");
            bookOverviewWindow.refreshEntries(getEntries());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editEntry(String[] values) {
        String editedEntry = values[0];
        String editedAmount = values[1];
        String selectedEntryData = bookOverviewWindow.getSelectedEntry().split(";")[2];
        try {
            JDBC_mysql_connector.execUpdate("UPDATE "+openedBookName+"_book " +
                    "SET entry='"+editedEntry+"', " +
                    "amount='"+editedAmount+"' " +
                    "WHERE (entry_date='"+selectedEntryData+"');");
            bookOverviewWindow.refreshEntries(getEntries());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void backToList(){
        bookOverviewWindow.dispose();
        SimpleLogic.showWindow();
    }
}
