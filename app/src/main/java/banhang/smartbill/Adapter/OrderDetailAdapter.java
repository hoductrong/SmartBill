package banhang.smartbill.Adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Activity.MainActivity;
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
    private OnUpdateSumPrice onUpdateSumPrice; //update sum price of order when productorder change
    public OrderDetailAdapter(Activity context, int layoutID, List<OrderProduct> objects) {
        super(context, layoutID, objects);
        this.context = context;
        this.dshoadon = objects;

    }
    @Override
    public int getCount() {
        return dshoadon.size();
    }
    @Nullable
    @Override
    public OrderProduct getItem(int position) {
        OrderProduct product = dshoadon.get(position);
        return product;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chitiethoadon_listview_custom, null, false);
            holder.etCount = (EditText) convertView.findViewById(R.id.et_count);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.ibRemoveProduct = (ImageButton) convertView.findViewById(R.id.ib_remove_shopping_cart);
            convertView.setTag(holder);

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

        holder.etCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(dshoadon.size() <= 0)
                        return;
                    dshoadon.get(holder.ref).setAmount(
                            Float.valueOf(holder.etCount.getText().toString()));
                    notifyDataSetChanged();
                }
            }
        });
        holder.etCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    holder.etCount.clearFocus();
                    return false;
                }
                return false;
            }
        });
        holder.ibRemoveProduct.setOnClickListener(removeProduct(position));
        return convertView;
    }
    private class ViewHolder {
        TextView tvName;
        EditText etCount;
        TextView tvPrice;
        ImageButton ibRemoveProduct;
        int ref;
    }
    private View.OnClickListener removeProduct(final int row) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderProduct o = getItem(row);
                if( MainActivity.CurrentOrder.getOrderProducts()!=null) {
                    dshoadon.remove(o);
                    MainActivity.CurrentOrder.getOrderProducts().remove(o);
                    notifyDataSetChanged();
                }

            }
        };
    }

    //callback update sum price on Fragment's view element
    public void setOnUpdateSumPrice(OnUpdateSumPrice onUpdateSumPrice){
        this.onUpdateSumPrice = onUpdateSumPrice;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //update sum price of order
        this.onUpdateSumPrice.UpdateSumPrice(MainActivity.CurrentOrder.getSumMoney());
    }

    ///this interface used to create callback to Fragment to update screen
    public interface OnUpdateSumPrice{
        void UpdateSumPrice(float sumPrice);
    }
}



