package Logic.FileInteraction;

import Logic.InfoModels.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;

public class DataWriter {
    public void saveBooks(HashMap<String, Book> books, String outDir){
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        for(Book book:books.values()) {
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
