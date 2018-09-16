package Logic.FileInteraction;

import Logic.InfoModels.IncomeBook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataWriter {
    private Gson gsonBuilder;
    public DataWriter(){
        gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        IncomeBook incBook = new IncomeBook();
        incBook.setBookName("test");
        incBook.addEntry("check123",4000,"2018-09-13 04:56");
        incBook.addEntry("check234",4000,"2018-09-14 13:48");
        incBook.addEntry("check345",4000,"2018-09-16 17:35");
        String json = gsonBuilder.toJson(incBook);
        System.out.println(json);
    }
}
