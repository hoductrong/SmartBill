package banhang.smartbill.DAL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by KARATA on 28/11/2017.
 */

public class MyGsonBuilder {
    public Gson create(){
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return builder.create();
    }
}
