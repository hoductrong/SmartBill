package banhang.smartbill.DAL;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

import banhang.smartbill.Entity.GrantTokenResult;

/**
 * Created by KARATA on 28/11/2017.
 */

public class TokenAPI {
    public static String TOKEN = null;

    public String getToken(){
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/token");
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept","application/json");
            String content = "grant_type=password&username=dinhhongphi&password=phi123";
            GrantTokenResult result = tool.getResult(conn,content,GrantTokenResult.class);
            TOKEN = result.getAccess_token();
            return result.getAccess_token();
        }catch(ProtocolException ex){
            return null;
        }
    }
}
