package Logic;

import GUI.Windows.BooksListWindow;
import Logic.FileInteraction.DataReader;
import Logic.FileInteraction.DataWriter;
import Logic.InfoModels.*;
import com.opencsv.CSVWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;

public class BookListLogic {
    private static final int INCOME_COL_NUMBER = 3;
    private static final int DEBT_COL_NUMBER = 4;
    private static final String[] INCOME_BOOK_HEADERS = {"Entry","Amount","Date"};
    private static final String[] DEBT_BOOK_HEADERS = {"Entry","Amount","Date","Status"};
    private static HashMap<String, Book> books = new HashMap<>();
    private static String workDir;

    private static BooksListWindow booksListWindow;
    public static void main(String[] args){
        File files;
        try {
            files = new File(Paths.get(BookListLogic.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent()+"/books");
            workDir = files.getPath();
            books = new DataReader().loadBooks(files.getPath(),files.list());
            showWindow();
        } catch (URISyntaxException e) { e.printStackTrace(); }
    }
    static void showWindow(){
        booksListWindow = new BooksListWindow();
        booksListWindow.refreshList(getBooks());
    }
    private static String[] getBooks() {
        String booksNames[] = books.keySet().toArray(new String[books.size()]);
        for (int i = 0; i < booksNames.length; i++) {
            booksNames[i] +=";"+books.get(booksNames[i]).getBookType();
        }
        return booksNames;
    }
    public static void saveBooks(){
        new DataWriter().saveBooks(books,workDir);
    }
    static Book getBook(String bookName){
        return books.get(bookName);
    }
    public static String getSelectedBook(){
        return booksListWindow.getSelectedBook();
    }
    public static void addBook(String bookName, String bookType) {
        Book book;
        if(bookType.equals("income")){
            book = new IncomeBook(bookName);
        } else {
            book = new DebtBook(bookName);
        }
        book.setBookType(bookType);
        books.put(bookName, book);
        saveBooks();
        booksListWindow.refreshList(getBooks());
    }
    public static void deleteBook(){
        String bookName = booksListWindow.getSelectedBook().split(";")[0];
        File del = new File(workDir +"/"+ bookName + ".json");
        del.setWritable(true);
        del.delete();
        books.remove(bookName);
        booksListWindow.refreshList(getBooks());
        booksListWindow.validate();
        booksListWindow.invalidate();
        booksListWindow.revalidate();
        booksListWindow.repaint();
        booksListWindow.update(booksListWindow.getGraphics());
    }
    public static void openBook() {
        String bookName = booksListWindow.getSelectedBook();
        booksListWindow.dispose();
        BookOverviewLogic.setOpenedBookName(bookName);
        BookOverviewLogic.setOpenedBookType(books.get(bookName).getBookType());
        BookOverviewLogic.showWindow(getBookEntries(bookName));
    }
    static Entry[] getBookEntries(String bookName){
        return books.get(bookName).getEntries();
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
        Entry[] entries = getBookEntries(bookName);
        for(int r = 0; r < entries.length+1;r++) {
            Row row = sheet.createRow(r);
            if(getBook(bookName).getBookType().equals("income")) {
                for (int c = 0; c < INCOME_COL_NUMBER; c++) {
                    Cell cell = row.createCell(c);
                    if (r > 0 && c == 0) {
                        cell.setCellValue(entries[r - 1].getLabel());
                    } else if (r > 0 && c == 1) {
                        cell.setCellValue(entries[r - 1].getAmount());
                    } else if (r > 0 && c == 2) {
                        cell.setCellValue(entries[r - 1].getDate());
                    } else {
                        cell.setCellValue(INCOME_BOOK_HEADERS[c]);
                    }
                    sheet.autoSizeColumn(c);
                }
            } else {
                for (int c = 0; c < DEBT_COL_NUMBER; c++) {
                    Cell cell = row.createCell(c);
                    if (r > 0 && c == 0) {
                        cell.setCellValue(entries[r - 1].getLabel());
                    } else if (r > 0 && c == 1) {
                        cell.setCellValue(entries[r - 1].getAmount());
                    } else if (r > 0 && c == 2) {
                        cell.setCellValue(entries[r - 1].getDate());
                    } else if (r > 0 && c == 3) {
                        cell.setCellValue(((DebtEntry)entries[r - 1]).isRepaid());
                    } else {
                        cell.setCellValue(DEBT_BOOK_HEADERS[c]);
                    }
                    sheet.autoSizeColumn(c);
                }
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
        Entry[] entries = getBookEntries(bookName);
        for (int r=0;r < entries.length+1; r++ ) {
            XSSFRow row = sheet.createRow(r);
            if(getBook(bookName).getBookType().equals("income")) {
                for (int c = 0; c < INCOME_COL_NUMBER; c++) {
                    XSSFCell cell = row.createCell(c);
                    if (r > 0 && c == 0) {
                        cell.setCellValue(entries[r - 1].getLabel());
                    } else if (r > 0 && c == 1) {
                        cell.setCellValue(entries[r - 1].getAmount());
                    } else if (r > 0 && c == 2) {
                        cell.setCellValue(entries[r - 1].getDate());
                    } else {
                        cell.setCellValue(INCOME_BOOK_HEADERS[c]);
                    }
                    sheet.autoSizeColumn(c);
                }
            } else {
                for (int c = 0; c < DEBT_COL_NUMBER; c++) {
                    Cell cell = row.createCell(c);
                    if (r > 0 && c == 0) {
                        cell.setCellValue(entries[r - 1].getLabel());
                    } else if (r > 0 && c == 1) {
                        cell.setCellValue(entries[r - 1].getAmount());
                    } else if (r > 0 && c == 2) {
                        cell.setCellValue(entries[r - 1].getDate());
                    } else if (r > 0 && c == 3) {
                        cell.setCellValue(((DebtEntry)entries[r - 1]).isRepaid());
                    } else {
                        cell.setCellValue(DEBT_BOOK_HEADERS[c]);
                    }
                    sheet.autoSizeColumn(c);
                }
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
            Entry[] entries = getBookEntries(bookName);
            if(getBook(bookName).getBookType().equals("income")) {
                csvWriter.writeNext(INCOME_BOOK_HEADERS);
                for(Entry s : entries)
                    csvWriter.writeNext(new String[]{s.getLabel(),s.getAmount()+"",s.getDate()});
            } else {
                csvWriter.writeNext(DEBT_BOOK_HEADERS);
                for(Entry s : entries)
                    csvWriter.writeNext(new String[]{s.getLabel(),s.getAmount()+"",s.getDate(),((DebtEntry)s).isRepaid()});
            }
            csvWriter.close();
            fWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}