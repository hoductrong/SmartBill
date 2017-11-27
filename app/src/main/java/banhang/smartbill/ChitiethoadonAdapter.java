package banhang.smartbill;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MyPC on 25/11/2017.
 */

public class ChitiethoadonAdapter extends ArrayAdapter<ItemTest> {
    private Activity context;
    private List<ItemTest> dshoadon;
    private String[] arrTemp ;



    public ChitiethoadonAdapter(Activity context, int layoutID, List<ItemTest> objects) {
        super(context, layoutID, objects);
        this.context = context;
        this.dshoadon = objects;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chitiethoadon_listview_custom, null,
                    false);
            convertView.setTag(holder);
            holder.editText1 = (EditText) convertView.findViewById(R.id.et_count);
        }
        else holder = (ViewHolder) convertView.getTag();


        final ItemTest hoadon = getItem(position);


        // Get view
        holder.ref = position;
        ImageView ivShoppingCart = (ImageView) convertView.findViewById(R.id.iv_shopping_cart);
        ImageButton ibRemove = (ImageButton) convertView.findViewById(R.id.ib_remove_shopping_cart);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
        EditText etNumber = (EditText) convertView.findViewById(R.id.et_count);


        if (hoadon.getName() != null) {
            tvName.setText(hoadon.getName());
        } else tvName.setText("");

        if (hoadon.getPrice() != null) {
            tvPrice.setText(hoadon.getPrice());
        } else tvPrice.setText("");

        holder.editText1.setText(Integer.toString(dshoadon.get(position).getNumber()));
        holder.editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    dshoadon.get(holder.ref).setNumber(Integer.parseInt(((EditText) view).getText().toString()));
                }

            }
        });


        return convertView;
    }
    private class ViewHolder {
        TextView textView1;
        EditText editText1;
        int ref;
    }
}



