package Logic;

import GUI.Windows.BooksListWindow;
import Logic.FileInteraction.DataReader;
import Logic.FileInteraction.DataWriter;
import Logic.InfoModels.IncomeBook;
import Logic.InfoModels.IncomeEntry;
import com.opencsv.CSVWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;

public class BookListLogic {
    private static final int COL_NUMBER = 3;
    private static final String[] INCOME_BOOK_HEADERS = {"Entry","Amount","Date"};
    private static HashMap<String, IncomeBook> books = new HashMap<>();
    private static String workDir = "D:\\Java\\IdeaProjects\\Desktop\\ledger\\src\\main\\resources\\books\\";

    private static BooksListWindow booksListWindow;
    public static void main(String[] args){
        File files = new File(workDir);
        books = new DataReader().loadBooks(files.getPath(),files.list());
        showWindow();
    }
    static void showWindow(){
        booksListWindow = new BooksListWindow();
        booksListWindow.refreshList(getBooks());
    }
    private static String[] getBooks() {
        String booksNames[] = books.keySet().toArray(new String[books.size()]);
        //TODO: move book type to object
        for (int i = 0; i < booksNames.length; i++) {
            booksNames[i] +=";income";
        }
        return booksNames;
    }
    public static void saveBooks(){
        new DataWriter().saveBooks(books,workDir);
    }
    static IncomeBook getBook(String bookName){
        return books.get(bookName);
    }
    public static String getSelectedBook(){
        return booksListWindow.getSelectedBook();
    }
    public static void addBook(String bookName) {
        IncomeBook book = new IncomeBook();
        book.setBookName(bookName);
        books.put(bookName, book);
        saveBooks();
        booksListWindow.refreshList(getBooks());
    }
    public static void deleteBook(){
        String bookName = booksListWindow.getSelectedBook().split(";")[0];
        File del = new File(workDir + bookName + ".json");
        del.delete();
        books.remove(bookName);
        booksListWindow.refreshList(getBooks());
    }

    public static void openBook() {
        String bookName = booksListWindow.getSelectedBook();
        booksListWindow.dispose();
        BookOverviewLogic.setOpenedBookName(bookName);
        BookOverviewLogic.showWindow(getBookEntries(bookName));
    }

    static String[] getBookEntries(String bookName){
        IncomeEntry[] entries = books.get(bookName).getEntries();
        String[] returnableEntries = new String[entries.length];
        for(int i=0;i<entries.length; i++){
            //TODO: adapt view of entries to work with fields instead of strings
            returnableEntries[i] = entries[i].getLabel()+";"+entries[i].getAmount()+";"+entries[i].getDate();
        }
        return returnableEntries;
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
            case ".html":
                exportToHtml(bookName, saveTo);
                break;
        }
    }

    private static void exportToHtml(String bookName, String saveTo) {
        try {
            HTMLExporter htmlExporter = new HTMLExporter(new FileWriter(saveTo+"/"+bookName+".html", false), bookName);
            htmlExporter.setData(getBookEntries(bookName));
            htmlExporter.writeData();
            htmlExporter.closeHTMLExporter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exportToXls(String bookName, String saveTo) {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet(bookName);
        String[] entries = getBookEntries(bookName);
        for(int r = 0; r < entries.length+1;r++){
            Row row = sheet.createRow(r);
            for (int c = 0; c < COL_NUMBER; c++) {
                Cell cell = row.createCell(c);
                if(r>0) {
                    cell.setCellValue(entries[r-1].split(";")[c]);
                } else {
                    cell.setCellValue(INCOME_BOOK_HEADERS[c]);
                }
                sheet.autoSizeColumn(c);
            }
        }
        try {
            FileOutputStream fOut = new FileOutputStream(saveTo+"/"+bookName+".xls");
            book.write(fOut);
            book.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exportToXlsx(String bookName, String saveTo) {
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet(bookName);
        String[] entries = getBookEntries(bookName);
        for (int r=0;r < entries.length+1; r++ ) {
            XSSFRow row = sheet.createRow(r);
            for (int c = 0; c < COL_NUMBER; c++) {
                XSSFCell cell = row.createCell(c);
                if(r>0) {
                    cell.setCellValue(entries[r-1].split(";")[c]);
                } else {
                    cell.setCellValue(INCOME_BOOK_HEADERS[c]);
                }
                sheet.autoSizeColumn(c);
            }
        }
        try {
            FileOutputStream fOut = new FileOutputStream(saveTo+"/"+bookName+".xlsx");
            book.write(fOut);
            book.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exportToCsv(String bookName, String saveTo) {
        try {
            FileWriter fWriter = new FileWriter(saveTo+"/"+bookName+".csv", false);
            CSVWriter csvWriter = new CSVWriter(fWriter,';',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
            csvWriter.writeNext(INCOME_BOOK_HEADERS);
            String[] entries = getBookEntries(bookName);
            for(String s : entries)
                csvWriter.writeNext(s.split(";"));
            csvWriter.close();
            fWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}