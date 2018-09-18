package Logic;

import Logic.InfoModels.IncomeEntry;

import java.io.IOException;
import java.io.Writer;

class HTMLExporter {
    private String outHTMLCode="";
    private Writer outWriter;
    private IncomeEntry[] data;
    private String bookName;
    HTMLExporter(Writer outWriter, String bookName){
        this.outWriter = outWriter;
        this.bookName = bookName;
    }
    void setData(IncomeEntry[] data){
        this.data = data;
    }
    void writeData(){
        String tableData="";
        for(IncomeEntry s:data){
            tableData+="<tr>\n" +
            "               <td style=\"border:1px solid black;\">"+s.getLabel()+"</td>\n" +
            "               <td style=\"border:1px solid black;\">"+s.getAmount()+"</td>\n" +
            "               <td style=\"border:1px solid black;\">"+s.getDate()+"</td>\n" +
            "           </tr>\n";
        }
        outHTMLCode+="<!DOCTYPE html>\n" +
                     "<html>\n" +
                     "    <head><title>"+bookName+"</title></head>\n" +
                     "    <body>\n" +
                     "        <table style=\"border-collapse: collapse;\">\n" +
                     "           <th style=\"border:1px solid black;\">Entry</th>"+
                     "           <th style=\"border:1px solid black;\">Amount</th>"+
                     "           <th style=\"border:1px solid black;\">Data</th>"+
                                 tableData +
                     "        </table>\n" +
                     "    </body>\n" +
                     "</html>";
        try {
            outWriter.write(outHTMLCode);
        } catch (IOException e) { e.printStackTrace(); }
    }
    void closeHTMLExporter(){
        try {
            outWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}