package Logic.InfoModels;

import java.util.ArrayList;

public class IncomeBook {
    private String bookName;
    private ArrayList<IncomeEntry> entries = new ArrayList<>();

    public void setBookName(String bookName){
        this.bookName = bookName;
    }
    public String getBookName(){
        return bookName;
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
    public void editEntry(int id, String label, int amount, String date){
        entries.get(id).setLabel(label);
        entries.get(id).setAmount(amount);
        entries.get(id).setDate(date);
    }
}
