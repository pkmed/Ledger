package Logic.InfoModels;

public class IncomeEntry implements Entry{
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
    public void setLabel(String label){
        this.label = label;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount){
        this.amount = amount;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
}
