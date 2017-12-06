package banhang.smartbill.Adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import banhang.smartbill.Entity.OrderProduct;
import banhang.smartbill.Entity.Product;
import banhang.smartbill.ItemTest;
import banhang.smartbill.R;

/**
 * Created by MyPC on 25/11/2017.
 */

public class OrderDetailAdapter extends ArrayAdapter<OrderProduct> {
    private Activity context;
    private List<OrderProduct> dshoadon;

    public OrderDetailAdapter(Activity context, int layoutID, List<OrderProduct> objects) {
        super(context, layoutID, objects);
        this.context = context;
        this.dshoadon = objects;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chitiethoadon_listview_custom, null, false);
            convertView.setTag(holder);
            holder.etCount = (EditText) convertView.findViewById(R.id.et_count);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);

        }
        else holder = (ViewHolder) convertView.getTag();
        final OrderProduct hoadon = getItem(position);
        holder.ref = position;

        if (hoadon.getProduct().getName() != null) {
            holder.tvName.setText(hoadon.getProduct().getName());
        } else holder.tvName.setText("");

        if (Long.toString(hoadon.getProduct().getUnitPrice()) != null) {
            holder.tvPrice.setText(Long.toString(hoadon.getProduct().getUnitPrice()));
        } else holder.tvPrice.setText("");

        holder.etCount.setText(Float.toString(dshoadon.get(position).getAmount()));
        //Them su kien de khi scroll thi edittext khong bi mat
        holder.etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dshoadon.get(holder.ref).setAmount(Float.valueOf(editable.toString()));
            }
        });

        return convertView;
    }
    private class ViewHolder {
        TextView tvName;
        EditText etCount;
        TextView tvPrice;
        int ref;
    }
}



