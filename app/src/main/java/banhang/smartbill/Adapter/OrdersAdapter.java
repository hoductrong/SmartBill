package banhang.smartbill.Adapter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import banhang.smartbill.Activity.MainActivity;
import banhang.smartbill.DAL.OrdersAPI;
import banhang.smartbill.Entity.Order;
import banhang.smartbill.Entity.UnauthorizedAccessException;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.main_item, null,
                    false);
            holder.tv_customer_name = convertView.findViewById(R.id.tv_customer_name);
            holder.tv_create_date = convertView.findViewById(R.id.tv_create_date);
            holder.tv_status = convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Order order = getItem(position);
        //holder.tv_customer_name.setText(order.getCustomer().getName());
        //format datatime
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        holder.tv_create_date.setText(format.format(order.getCreateDate()));
        if (order.isPaid()) {
            holder.tv_status.setText(R.string.order_is_paid);
        } else {
            holder.tv_status.setText(R.string.order_not_paid);
        }
        ///if current order
        if(position == 0 && MainActivity.CurrentOrder != null
                && order.getId() == MainActivity.CurrentOrder.getOrder().getId())
        {
            holder.tv_status.setText(R.string.this_is_current_order);
        }
        //assign event to remove order
        ImageButton btn_remove = (ImageButton) convertView.findViewById(R.id.btn_remove);
        btn_remove.setOnClickListener(getRemoveOrderHandler(position));

        return convertView;
    }

    ///handler remove order on view and server,
    /// remove MainActivity.CurrentOrder if this order is currently
    private View.OnClickListener getRemoveOrderHandler(final int row) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order o = getItem(row);
                if (row == 0) {
                    if (MainActivity.CurrentOrder != null &&
                            o.getId() == MainActivity.CurrentOrder.getOrder().getId()) {
                        //this is current order
                        MainActivity.CurrentOrder = null;
                        //require garbage free memory
                        Runtime.getRuntime().gc();
                        OrdersAdapter.this.remove(o);
                        OrdersAdapter.this.notifyDataSetChanged();
                        return;
                    }
                }
                //remove order from server
                removeOrderOnServer(o);
                OrdersAdapter.this.remove(o);
                OrdersAdapter.this.notifyDataSetChanged();
            }
        };
    }

    ///call api to remove order on server
    private void removeOrderOnServer(final Order order) {
        final android.os.Handler handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Toast.makeText(getContext(), "Xóa hóa đơn thành công", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        //require authorize again
                        MainActivity.requireLogin(getContext());
                }
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OrdersAPI api = new OrdersAPI();
                try {
                    api.deleteOrder(order.getId());
                    Message message = handler.obtainMessage(1);
                    handler.sendMessage(message);
                } catch (UnauthorizedAccessException ex) {
                    Message message = handler.obtainMessage(2);
                    handler.sendMessage(message);
                }
            }
        });
        thread.start();
    }

    public class ViewHolder {
        public TextView tv_customer_name;
        public TextView tv_create_date;
        public TextView tv_status;
    }
}
