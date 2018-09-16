package Logic.InfoModels;

public class IncomeEntry {
    private String label;
    private int amount;
    private String date;
    IncomeEntry(String label, int amount, String date){
        this.label = label;
        this.amount = amount;
        this.date = date;
    }
    public String getLabel(){
        return label;
    }
    void setLabel(String label){
        this.label = label;
    }
    public int getAmount() {
        return amount;
    }
    void setAmount(int amount){
        this.amount = amount;
    }
    public String getDate() {
        return date;
    }
    void setDate(String date){
        this.date = date;
    }
}
