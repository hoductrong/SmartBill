package banhang.smartbill.Utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Activity.MainActivity;
import banhang.smartbill.Adapter.CustomerListAdapter;
import banhang.smartbill.DAL.CustomerAPI;
import banhang.smartbill.Entity.Customer;
import banhang.smartbill.Entity.UnauthorizedAccessException;
import banhang.smartbill.Fragment.OrderFragment;
import banhang.smartbill.R;

/**
 * Created by KARATA on 05/12/2017.
 */

public class CreateOrderDialog extends Dialog {
    private RelativeLayout btn_close;
    private Button btn_create_order;
    private AutoCompleteTextView tv_customer;
    private CustomerListAdapter adapter;

    public CreateOrderDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        btn_close = (RelativeLayout) findViewById(R.id.btn_close);
        btn_create_order = (Button) findViewById(R.id.btn_create_order);
        tv_customer = (AutoCompleteTextView) findViewById(R.id.tv_customer);

        //get customer list
        getCustomerList();
    }

    private void getCustomerList() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1: //get success
                        //set data for autoComplete textView
                        ///set adapter for autocomplete
                        ArrayList<Customer> data = (ArrayList<Customer>) msg.obj;
                        adapter = new CustomerListAdapter(getContext(),
                                R.layout.customer_name_list_item, data);
                        tv_customer.setAdapter(adapter);
                        tv_customer.addTextChangedListener(getTextWatcher());
                        break;
                    case 2: //unauthorize
                        Toast.makeText(getContext(), R.string.unauthorize, Toast.LENGTH_LONG).show();
                        MainActivity.requireLogin(getContext());
                        break;
                }
            }
        };

        Thread getCustomerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CustomerAPI api = new CustomerAPI();
                try {
                    List<Customer> customerList = api.getCustomers();
                    Message message = handler.obtainMessage(1, customerList);
                    handler.sendMessage(message);
                } catch (UnauthorizedAccessException ex) {
                    Message message = handler.obtainMessage(2, "Unauthorize");
                    handler.sendMessage(message);
                }
            }
        });
        getCustomerThread.start();
    }

    //enable create order button when found customer
    private TextWatcher getTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(adapter.find(charSequence.toString()) != null)
                    btn_create_order.setEnabled(true);
                else
                    btn_create_order.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }


    //handle action when close button is cliked
    public void setOnCloseButtonClickListener(View.OnClickListener listener) {
        if (listener != null)
            btn_close.setOnClickListener(listener);
        else
            throw new NullPointerException();
    }

    //handle action when create order clicked
    public void setOnCreateOrderClickListener(View.OnClickListener listener) {
        if (listener != null) {
            btn_create_order.setOnClickListener(listener);
        } else
            throw new NullPointerException();
    }

    //return customer is selected
    public Customer getSelectedCustomer(){
        return adapter.find(tv_customer.getText().toString());
    }
}
