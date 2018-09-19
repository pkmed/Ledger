package Logic;

import GUI.Windows.BookOverviewWindow;
import Logic.InfoModels.DebtBook;
import Logic.InfoModels.Entry;

public class BookOverviewLogic {
    private static String openedBookName;
    private static String openedBookType;
    private static BookOverviewWindow bookOverviewWindow;
    static void showWindow(Entry[] entries){
        bookOverviewWindow = new BookOverviewWindow(openedBookName,openedBookType);
        bookOverviewWindow.refreshEntries(entries);
    }

    static void setOpenedBookName(String bookName) {
        openedBookName = bookName;
    }
    static void setOpenedBookType(String bookType) {
        openedBookType = bookType;
    }

    private static Entry[] getEntries(){
        return BookListLogic.getBookEntries(openedBookName);
    }
    public static String getOpenedBookType(){
        return openedBookType;
    }
    public static String getSelectedEntry(){
        return bookOverviewWindow.getSelectedEntry();
    }

    public static void addEntry(String[] values) {
        BookListLogic.getBook(openedBookName).addEntry(values[0], Integer.parseInt(values[1]), values[2]);
        bookOverviewWindow.refreshEntries(getEntries());
    }

    public static void removeEntry() {
        BookListLogic.getBook(openedBookName).deleteEntry(bookOverviewWindow.getSelectedEntryId());
        bookOverviewWindow.refreshEntries(getEntries());
    }

    public static void editEntry(String[] values) {
        String editedEntry = values[0];
        int editedAmount = Integer.parseInt(values[1]);
        String selectedEntryData = bookOverviewWindow.getSelectedEntry().split(";")[2];
        BookListLogic.getBook(openedBookName).editEntry(bookOverviewWindow.getSelectedEntryId(),editedEntry,editedAmount,selectedEntryData);
        String status;
        if(values.length==4){
            status = values[3];
            ((DebtBook)BookListLogic.getBook(openedBookName)).editEntry(bookOverviewWindow.getSelectedEntryId(),editedEntry,editedAmount,selectedEntryData,status);
        }
        bookOverviewWindow.refreshEntries(getEntries());
    }

    public static void backToList(){
        bookOverviewWindow.dispose();
        BookListLogic.saveBooks();
        BookListLogic.showWindow();
    }
}
