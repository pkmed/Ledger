package Logic;

import GUI.Windows.BooksListWindow;
import com.opencsv.CSVWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import wrap.JDBC_mysql_connector;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookListLogic {
    private static final int COL_NUMBER = 3;
    private static final String[] INCOME_BOOK_HEADERS = {"Entry","Amount","Date"};

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
    public static String getSelectedBook(){
        return booksListWindow.getSelectedBook();
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
        String bookName = booksListWindow.getSelectedBook();
        booksListWindow.dispose();
        BookOverviewLogic.setOpenedBookName(bookName);
        BookOverviewLogic.showWindow(getBookEntries(bookName));
    }

    private static String[] getBookEntries(String bookName){
        ArrayList<String> entries = new ArrayList<>();
        try {
            ResultSet rs = JDBC_mysql_connector.execQuery("SELECT * FROM "+bookName+"_book");
            while (rs.next()){
                entries.add(rs.getString(2)+";"+rs.getString(3)+";"+rs.getString(4));
            }
            return entries.toArray(new String[entries.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void exportBook(String bookName, String exportType, String saveTo){
        switch(exportType){
            case ".xls":
                exportToXls(bookName, saveTo);
                break;
            case ".xlsx":
                exportToXlsx(bookName, saveTo);
                break;
            case ".csv":
                exportToCsv(bookName, saveTo);
                break;
        }
    }

    private static void exportToXls(String bookName, String saveTo) {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet(bookName);
        String[] entries = getBookEntries(bookName);
        for(int r = 0; r < entries.length;r++){
            if(r>0){
                Row row = sheet.createRow(r);
                for (int c = 0; c < COL_NUMBER; c++) {
                    Cell cell = row.createCell(c);
                    cell.setCellValue(entries[r].split(";")[c]);
                    sheet.autoSizeColumn(c);
                }
            } else {
                Row row = sheet.createRow(r);
                for (int c = 0; c < COL_NUMBER; c++) {
                    Cell cell = row.createCell(c);
                    cell.setCellValue(INCOME_BOOK_HEADERS[c]);
                    sheet.autoSizeColumn(c);
                }
            }
        }
        try {
            book.write(new FileOutputStream(saveTo+"/"+bookName+".xls"));
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exportToXlsx(String bookName, String saveTo) {
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet(bookName);
        String[] entries = getBookEntries(bookName);
        for (int r=0;r < entries.length; r++ ) {
            if(r>0){
                XSSFRow row = sheet.createRow(r);
                for (int c = 0; c < COL_NUMBER; c++) {
                    XSSFCell cell = row.createCell(c);
                    cell.setCellValue(entries[r].split(";")[c]);
                    sheet.autoSizeColumn(c);
                }
            } else {
                XSSFRow row = sheet.createRow(r);
                for (int c = 0; c < COL_NUMBER; c++) {
                    XSSFCell cell = row.createCell(c);
                    cell.setCellValue(INCOME_BOOK_HEADERS[c]);
                    sheet.autoSizeColumn(c);
                }
            }
        }
        try {
            book.write(new FileOutputStream(saveTo+"/"+bookName+".xlsx"));
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exportToCsv(String bookName, String saveTo) {
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(saveTo+"/"+bookName+".csv", false),';',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
            csvWriter.writeNext(new String[]{"Entry","Amount","Date"});
            String[] entries = getBookEntries(bookName);
            for(String s : entries)
                csvWriter.writeNext(s.split(";"));
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}