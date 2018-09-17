package Logic.FileInteraction;

import Logic.InfoModels.IncomeBook;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataReader {
    public HashMap<String, IncomeBook> loadBooks(String dir, String[] files) {
        HashMap<String, IncomeBook> books = new HashMap<>();
        Gson loader;
        try{
            for(String file:files){
                if(file.contains(".json")) {
                    loader = new Gson();
                    FileReader fr = new FileReader(dir + "\\" + file);
                    IncomeBook book = loader.fromJson(fr, IncomeBook.class);
                    books.put(book.getBookName(), book);
                    fr.close();
                }
            }
            return books;
        }catch(FileNotFoundException exc){
            exc.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
