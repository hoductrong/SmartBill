package banhang.smartbill.DAL;

import android.renderscript.ScriptGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import banhang.smartbill.Entity.Order;
import banhang.smartbill.Entity.UnauthorizedAccessException;

/**
 * Created by KARATA on 28/11/2017.
 */

public class BaseAPI {
    private URL url;
    private HttpURLConnection connection;

    public BaseAPI(String urlString) {
        try {
            url = new URL(urlString);
        } catch (MalformedURLException ex) {

        }
    }

    public HttpURLConnection getConnection() {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            return null;
        }
    }

    public <TResult> TResult getResult(HttpURLConnection connection, Class<TResult> classResult)
            throws UnauthorizedAccessException {
        try {
            //receive result
            checkHttpStatusCode(connection.getResponseCode());
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));
            Gson gson = new MyGsonBuilder().create();
            return gson.fromJson(br, classResult);
        } catch (IOException ex) {
            return null;
        }
    }

    public <TResult> TResult getResult(HttpURLConnection connection, Type resultType)
             throws UnauthorizedAccessException{
        try {
            //receive result
            checkHttpStatusCode(connection.getResponseCode());
            InputStream inputStream = connection.getInputStream();
            String resultData = readStream(inputStream);
            Gson gson = new MyGsonBuilder().create();
            TResult data = gson.fromJson(resultData, resultType);
            return data;
        } catch (IOException ex) {
            return null;
        }
    }


    public <TResult> TResult getResult(HttpURLConnection connection, Object content, Type resultType)
            throws UnauthorizedAccessException {
        try {
            Gson gson = new MyGsonBuilder().create();

            //send content to server
            byte[] byteContent;
            if (!(content instanceof String)) {
                String sentContent = gson.toJson(content);
                byteContent = sentContent.getBytes();
            } else {
                byteContent = ((String) content).getBytes();
            }

            OutputStream os = connection.getOutputStream();
            os.write(byteContent);
            os.close();

            //receive result
            checkHttpStatusCode(connection.getResponseCode());
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));
            return gson.fromJson(br, resultType);
        } catch (IOException ex) {
            return null;
        }
    }

    public <TResult> TResult getResult(HttpURLConnection connection, Object content, Class<TResult> resultClass)
            throws UnauthorizedAccessException {
        try {
            Gson gson = new MyGsonBuilder().create();
            //send content to server
            byte[] byteContent;
            if (!(content instanceof String)) {
                String sentContent = gson.toJson(content);
                byteContent = sentContent.getBytes();
            } else {
                byteContent = ((String) content).getBytes();
            }

            OutputStream os = connection.getOutputStream();
            os.write(byteContent);
            os.close();

            //receive result
            checkHttpStatusCode(connection.getResponseCode());
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));
            return gson.fromJson(br, resultClass);
        } catch (IOException ex) {
            return null;
        }
    }

    protected void checkHttpStatusCode(int httpStatusCode) throws UnauthorizedAccessException {
        switch (httpStatusCode) {
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new UnauthorizedAccessException();
            case HttpURLConnection.HTTP_ACCEPTED:
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                break;
        }
    }

    private String readStream(InputStream stream) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }
}
