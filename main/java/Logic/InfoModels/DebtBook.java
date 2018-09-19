package Logic.InfoModels;

import java.util.ArrayList;

public class DebtBook implements Book {
    private String bookName;
    private String bookType;
    private ArrayList<DebtEntry> entries = new ArrayList<>();

    public DebtBook(String bookName){
        setBookName(bookName);
    }
    @Override
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String getBookName() {
        return bookName;
    }

    @Override
    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    @Override
    public String getBookType() {
        return bookType;
    }

    @Override
    public void addEntry(String entryLabel, int amount, String entryDate) {
        DebtEntry entry = new DebtEntry(entryLabel,amount,entryDate);
        entries.add(entry);
    }

    public DebtEntry[] getEntries() {
        return entries.toArray(new DebtEntry[entries.size()]);
    }

    public DebtEntry[] getEntries(int lowerBound, int upperBound) {
        DebtEntry[] entries = new DebtEntry[upperBound-lowerBound];
        for(int i = lowerBound; i<upperBound; i++){
            entries[i] = this.entries.get(i);
        }
        return entries;
    }

    public DebtEntry getEntry(int id) {
        return entries.get(id);
    }

    @Override
    public void deleteEntry(int id) {
        entries.remove(id);
    }

    @Override
    public void deleteEntry(String label, int amount, String date) {
        for(int i=0; i<entries.size();i++){
            if(entries.get(i).getLabel().equals(label) && entries.get(i).getAmount()==amount && entries.get(i).getDate().equals(date)){
                entries.remove(i);
                break;
            }
        }
    }

    @Override
    public void editEntry(int id, String label, int amount, String date) {
        entries.get(id).setLabel(label);
        entries.get(id).setAmount(amount);
        entries.get(id).setDate(date);
    }
}
