package banhang.smartbill.Adapter;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import banhang.smartbill.Entity.Order;
import banhang.smartbill.Hoadon;
import banhang.smartbill.R;

/**
 * Created by phuc-kun on 11/24/2017.
 */

public class OrdersAdapter extends ArrayAdapter<Order> {
    private Activity context;

    public OrdersAdapter(Activity context, int layoutID, List<Order> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.main_item, null,
                    false);
            holder.tv_customer_name = convertView.findViewById(R.id.tv_customer_name);
            holder.tv_create_date = convertView.findViewById(R.id.tv_create_date);
            holder.tv_status = convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Order order = getItem(position);
        //holder.tv_customer_name.setText(order.getCustomer().getName());
        //format datatime
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        holder.tv_create_date.setText(format.format(order.getCreateDate()));
        if(order.isPaid()){
            holder.tv_status.setText(R.string.order_is_paid);
        }else{
            holder.tv_status.setText(R.string.order_not_paid);
        }
        return convertView;
    }

    public class ViewHolder{
        public TextView tv_customer_name;
        public TextView tv_create_date;
        public TextView tv_status;
    }
}
