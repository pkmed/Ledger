package Logic.FileInteraction;

import Logic.InfoModels.IncomeBook;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class DataReader {
    public ArrayList<IncomeBook> loadBooks(String dir, String[] files) throws FileNotFoundException {
        ArrayList<IncomeBook> books = new ArrayList<>();
        for(String file:files){
            IncomeBook book = new Gson().fromJson(new FileReader(dir+"\\"+file),IncomeBook.class);
            books.add(book);
        }
        return books;
    }
}
