package Logic.InfoModels;

public interface Book {
    void setBookName(String bookName);
    String getBookName();
    void setBookType(String bookType);
    String getBookType();
    void addEntry(String entryLabel, int amount, String entryDate);
    Entry[] getEntries();
    Entry[] getEntries(int lowerBound, int upperBound);
    Entry getEntry(int id);
    void deleteEntry(int id);
    void deleteEntry(String label, int amount, String date);
    void editEntry(int id, String label, int amount, String date);
}
