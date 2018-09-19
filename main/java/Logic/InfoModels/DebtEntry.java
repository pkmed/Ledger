package Logic.InfoModels;

public class DebtEntry implements Entry {
    private String label;
    private int amount;
    private String date;
    private Boolean repaid;
    DebtEntry(String label, int amount, String date){
        this.label = label;
        this.amount = amount;
        this.date = date;
        this.repaid = false;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabel(){
        return label;
    }
    //TODO: add editing for debt entries
    public void setStatus(boolean isRepaid){
        repaid = isRepaid;
    }

    public String isRepaid() {
        return repaid.toString();
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
    public int getAmount(){
        return amount;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return date;
    }
}
