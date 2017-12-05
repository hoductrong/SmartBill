package banhang.smartbill.DAL;

import com.google.gson.reflect.TypeToken;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Entity.Customer;
import banhang.smartbill.Entity.Product;
import banhang.smartbill.Entity.UnauthorizedAccessException;

/**
 * Created by MyPC on 29/11/2017.
 */

public class CustomerAPI {
    public List<Customer> getCustomers()
            throws UnauthorizedAccessException {
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/customers");
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            if(TokenAPI.TOKEN != null)
                conn.setRequestProperty("Authorization","Bearer " + TokenAPI.TOKEN);

            ArrayList<Customer> customers = tool.getResult(conn,new TypeToken<ArrayList<Customer>>(){}.getType());
            System.out.println(customers);
            return customers;
        }catch(ProtocolException ex){
            return null;
        }
    }
    public Customer getCustomer(String id)
            throws UnauthorizedAccessException{
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/customers/"+id);
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            if(TokenAPI.TOKEN != null)
                conn.setRequestProperty("Authorization","Bearer " + TokenAPI.TOKEN);

            Customer customer = tool.getResult(conn,Customer.class);
            System.out.println(customer);
            return customer;
        }catch(ProtocolException ex){
            return null;
        }
    }
}
