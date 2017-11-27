package banhang.smartbill;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by phuc-kun on 11/24/2017.
 */

public class HoadonAdapter extends ArrayAdapter<Hoadon> {
    private Activity context;

    public HoadonAdapter(Activity context, int layoutID, List<Hoadon> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.main_item, null,
                    false);
        }
        Hoadon hoadon = getItem(position);
        TextView txtName = (TextView) convertView.findViewById(R.id.txt_name);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txt_date);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.txt_status);
        txtName.setText(hoadon.getName());
        txtDate.setText(hoadon.getNgaylap());
        txtStatus.setText(hoadon.getTrangthai());
        return convertView;
    }
}
