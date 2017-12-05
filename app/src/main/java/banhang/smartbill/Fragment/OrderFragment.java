package banhang.smartbill.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Activity.MainActivity;
import banhang.smartbill.Adapter.OrdersAdapter;
import banhang.smartbill.DAL.OrdersAPI;
import banhang.smartbill.Entity.Order;
import banhang.smartbill.Entity.UnauthorizedAccessException;
import banhang.smartbill.R;
import banhang.smartbill.Utility.CreateOrderDialog;

/**
 * Created by KARATA on 04/12/2017.
 */

public class OrderFragment extends Fragment {
    private OrdersAdapter orderAdapter;
    private List<Order> orderList;
    private FloatingActionButton btn_ad_order;

    public OrderFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_screen,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //init adapter to show order list
        ListView listView = getView().findViewById(R.id.lv_orders);
        btn_ad_order = getView().findViewById(R.id.add_order);

        orderList = new ArrayList<>();
        orderAdapter = new OrdersAdapter(getActivity(),R.layout.main_item,orderList);
        listView.setAdapter(orderAdapter);
        btn_ad_order.setOnClickListener(gethandlerAddOrder());

        //connect to server to get order data
        getAndShowOrders();
    }

    private View.OnClickListener gethandlerAddOrder(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CreateOrderDialog dialog = new CreateOrderDialog(getContext());
                dialog.setContentView(R.layout.create_order_dialog);
                dialog.setOnCloseButtonClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.setOnCreateOrderClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),"Bãn đã tạo hóa đơn",Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
            }
        };
    }

    private void getAndShowOrders(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1:
                        orderList.clear();
                        orderList.addAll((ArrayList<Order>)msg.obj);
                        orderAdapter.notifyDataSetChanged();
                        break;
                    case 2 : //error unauthorize
                        Toast.makeText(getContext(),R.string.unauthorize,Toast.LENGTH_LONG).show();
                        MainActivity.requireLogin(getContext());
                        break;

                }
            }
        };

        Thread getOrderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OrdersAPI api = new OrdersAPI();
                    List<Order> orders = api.getOrders();
                    Message message = handler.obtainMessage(1,orders);
                    handler.sendMessage(message);
                }catch(UnauthorizedAccessException ex){
                    Message message = handler.obtainMessage(2,"Unauthorize");
                    handler.sendMessage(message);
                }
            }
        });
        getOrderThread.start();
    }
}
