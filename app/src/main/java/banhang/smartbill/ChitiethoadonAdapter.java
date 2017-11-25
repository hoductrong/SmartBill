package banhang.smartbill;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MyPC on 25/11/2017.
 */

public class ChitiethoadonAdapter extends ArrayAdapter<HoaDon> {
    private Activity context;

    public ChitiethoadonAdapter(Activity context, int layoutID, List<HoaDon> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chitiethoadon_listview_custom, null,
                    false);
        }

        // Get item
        HoaDon hoadon = getItem(position);

        // Get view
        ImageView ivShoppingCart = (ImageView)convertView.findViewById(R.id.iv_shopping_cart);
        ImageButton ibRemove = (ImageButton)convertView.findViewById(R.id.ib_remove_shopping_cart);
        TextView tvName = (TextView)convertView.findViewById(R.id.tv_name);
        TextView tvPrice = (TextView)convertView.findViewById(R.id.tv_price);
        EditText etNumber = (EditText)convertView.findViewById(R.id.et_count);

        // Set fullname
        if (hoadon.getName()!=null) {
            tvName.setText(hoadon.getName());
        }
        else tvName.setText("");

        if (hoadon.getPrice()!=null) {
            tvPrice.setText(hoadon.getPrice());
        }
        else tvPrice.setText("");
     

        return convertView;
    }

}


