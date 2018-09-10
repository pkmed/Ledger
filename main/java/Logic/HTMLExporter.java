package Logic;

import java.io.IOException;
import java.io.Writer;

class HTMLExporter {
    private String outHTMLCode="";
    private Writer outWriter;
    private String[] data;
    private String bookName;
    HTMLExporter(Writer outWriter, String bookName){
        this.outWriter = outWriter;
        this.bookName = bookName;
    }
    void setData(String[] data){
        this.data = data;
    }
    void writeData(){
        String tableData="";
        for(String s:data){
            tableData+="<tr>\n" +
            "               <td>"+s.split(";")[0]+"</td>\n" +
            "               <td>"+s.split(";")[1]+"</td>\n" +
            "               <td>"+s.split(";")[2]+"</td>\n" +
            "           </tr>\n";
        }
        outHTMLCode+="<!DOCTYPE html>" +
                     "<html>\n" +
                     "    <head><p>"+bookName+"</p></head>\n" +
                     "    <body>\n" +
                     "        <table style=\"border:1px solid black;\">\n" +
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