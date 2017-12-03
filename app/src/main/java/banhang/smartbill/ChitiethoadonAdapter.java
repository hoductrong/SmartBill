package banhang.smartbill;

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

/**
 * Created by MyPC on 25/11/2017.
 */

public class ChitiethoadonAdapter extends ArrayAdapter<ItemTest> {
    private Activity context;
    private List<ItemTest> dshoadon;

    public ChitiethoadonAdapter(Activity context, int layoutID, List<ItemTest> objects) {
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
        final ItemTest hoadon = getItem(position);
        holder.ref = position;

        if (hoadon.getName() != null) {
            holder.tvName.setText(hoadon.getName());
        } else holder.tvName.setText("");

        if (hoadon.getPrice() != null) {
            holder.tvPrice.setText(hoadon.getPrice());
        } else holder.tvPrice.setText("");

        holder.etCount.setText(Integer.toString(dshoadon.get(position).getNumber()));
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
                dshoadon.get(holder.ref).setNumber(Integer.parseInt(editable.toString()));
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



