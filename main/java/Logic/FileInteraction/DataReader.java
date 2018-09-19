package Logic.FileInteraction;

import Logic.InfoModels.Book;
import Logic.InfoModels.DebtBook;
import Logic.InfoModels.DebtEntry;
import Logic.InfoModels.IncomeBook;
import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;

public class DataReader {
    public HashMap<String, Book> loadBooks(String dir, String[] files) {
        HashMap<String, Book> books = new HashMap<>();
        Gson loader;
        try{
            for(String file:files){
                if(file.contains(".json")) {
                    loader = new Gson();
                    FileReader fr = new FileReader(dir + "\\" + file);
                    //TODO: separate book creation depend on bookType
                    Book book;
                    if(bookType(dir + "\\" + file).equals("income")){
                        book = loader.fromJson(fr, IncomeBook.class);
                        books.put(book.getBookName(), book);
                    } else if(bookType(dir + "\\" + file).equals("debt")){
                        book = loader.fromJson(fr, DebtBook.class);
                        books.put(book.getBookName(), book);
                    }
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
    private String bookType(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String book="";
        while(br.ready()){
            book+=br.readLine();
        }
        if(book.contains("income")){
            br.close();
            return "income";
        } else if(book.contains("debt")){
            br.close();
            return "debt";
        } else{
            br.close();
            return null;
        }
    }
}
