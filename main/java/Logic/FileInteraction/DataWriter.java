package Logic.FileInteraction;

import Logic.InfoModels.IncomeBook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;

public class DataWriter {
    private Gson gsonBuilder;
    public void saveBooks(HashMap<String, IncomeBook> books, String outDir){
        gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        for(IncomeBook book:books.values()) {
            String outJson = gsonBuilder.toJson(book);
            try {
                File dir = new File( outDir+ "\\"+book.getBookName()+".json");
                dir.createNewFile();
                FileWriter fileOut = new FileWriter(outDir + "\\"+book.getBookName()+".json",false);
                fileOut.write(outJson);
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
