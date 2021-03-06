package Logic.InfoModels;

import java.util.ArrayList;

public class IncomeBook implements Book {
    private String bookName;
    private String bookType;
    private ArrayList<IncomeEntry> entries = new ArrayList<>();

    public IncomeBook(String bookName){
        setBookName(bookName);
    }
    public void setBookName(String bookName){
        this.bookName = bookName;
    }
    public String getBookName(){
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
    public void addEntry(String entryLabel, int amount, String entryDate){
        IncomeEntry entry = new IncomeEntry(entryLabel,amount,entryDate);
        entries.add(entry);
    }
    public IncomeEntry[] getEntries(){
        return entries.toArray(new IncomeEntry[entries.size()]);
    }
    public IncomeEntry[] getEntries(int lowerBound, int upperBound){
        IncomeEntry[] entries = new IncomeEntry[upperBound-lowerBound];
        for(int i = lowerBound; i<upperBound; i++){
            entries[i] = this.entries.get(i);
        }
        return entries;
    }
    public IncomeEntry getEntry(int id){
        return entries.get(id);
    }
    public void deleteEntry(int id){
        entries.remove(id);
    }
    public void deleteEntry(String label, int amount, String date){
        for(int i=0; i<entries.size();i++){
            if(entries.get(i).getLabel().equals(label) && entries.get(i).getAmount()==amount && entries.get(i).getDate().equals(date)){
                entries.remove(i);
                break;
            }
        }
    }
    public void editEntry(int id, String label, int amount, String date){
        entries.get(id).setLabel(label);
        entries.get(id).setAmount(amount);
        entries.get(id).setDate(date);
    }
}
