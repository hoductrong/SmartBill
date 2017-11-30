package banhang.smartbill.DAL;

import com.google.gson.reflect.TypeToken;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Entity.Customer;
import banhang.smartbill.Entity.Order;
import banhang.smartbill.Entity.OrderProduct;
import banhang.smartbill.Entity.Product;

/**
 * Created by MyPC on 29/11/2017.
 */

public class OrdersAPI {
    public List<Order> getOrders(){
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/orders");
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            if(TokenAPI.TOKEN != null)
                conn.setRequestProperty("Authorization","Bearer " + TokenAPI.TOKEN);

            ArrayList<Order> orders = tool.getResult(conn,new TypeToken<ArrayList<Order>>(){}.getType());
            System.out.println(orders);
            return orders;
        }catch(ProtocolException ex){
            return null;
        }
    }
    public Order getOrder(String id){
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/orders/"+id);
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            if(TokenAPI.TOKEN != null)
                conn.setRequestProperty("Authorization","Bearer " + TokenAPI.TOKEN);

            Order order = tool.getResult(conn,Order.class);
            System.out.println(order);
            return order;
        }catch(ProtocolException ex){
            return null;
        }
    }
    public Order postOrder(Order order){
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/orders");
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            if(TokenAPI.TOKEN != null)
                conn.setRequestProperty("Authorization","Bearer " + TokenAPI.TOKEN);

            Order getorder = tool.getResult(conn,order,Order.class);
            System.out.println(order);
            return getorder;
        }catch(ProtocolException ex){
            return null;
        }
    }
    public void deleteOrder(String id){
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/orders/"+id);
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            if(TokenAPI.TOKEN != null)
                conn.setRequestProperty("Authorization","Bearer " + TokenAPI.TOKEN);

            tool.getResult(conn,Order.class);
            System.out.println("");
        }catch(ProtocolException ex){
            ex.printStackTrace();
        }
    }
    public List<OrderProduct> putOrderProduct(String id, List<OrderProduct> products){
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/orders/"+id+"/products");
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            if(TokenAPI.TOKEN != null)
                conn.setRequestProperty("Authorization","Bearer " + TokenAPI.TOKEN);

            ArrayList<OrderProduct> getorder = tool.getResult(conn,products,new TypeToken<ArrayList<OrderProduct>>(){}.getType());
            System.out.println(getorder);
            return getorder;
        }catch(ProtocolException ex){
            return null;
        }
    }
    public List<Product> getOrderProduct(String id){
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/orders/"+id+"/products");
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            if(TokenAPI.TOKEN != null)
                conn.setRequestProperty("Authorization","Bearer " + TokenAPI.TOKEN);

            List<Product> getorder = tool.getResult(conn,new TypeToken<ArrayList<Product>>(){}.getType());
            System.out.println();
            return getorder;
        }catch(ProtocolException ex){
            return null;
        }
    }
    public Order putOrder(Order order){
        BaseAPI tool = new BaseAPI("http://quanlibanhang.azurewebsites.net/api/orders/"+order.getId());
        HttpURLConnection conn = tool.getConnection();
        try{
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            if(TokenAPI.TOKEN != null)
                conn.setRequestProperty("Authorization","Bearer " + TokenAPI.TOKEN);

            Order getorder = tool.getResult(conn,order,Order.class);
            System.out.println(getorder);
            return getorder;
        }catch(ProtocolException ex){
            return null;
        }
    }
}
