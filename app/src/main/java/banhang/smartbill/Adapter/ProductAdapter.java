package banhang.smartbill.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import banhang.smartbill.Entity.Product;
import banhang.smartbill.R;

/**
 * Created by MyPC on 04/12/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    private Activity context;
    public ProductAdapter(Activity context, int layoutID, List<Product> objects) {
        super(context, layoutID, objects);
        this.context = context;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_listview_custom, null, false);
        }

        final Product product = getItem(position);

        TextView tvProductName = convertView.findViewById(R.id.tv_product_name);
        ImageView ivShoppingcart = convertView.findViewById(R.id.iv_shopping_cart_product);


        if (product.getName() != null) {
            tvProductName.setText(product.getName());
        } else tvProductName.setText("");

        return convertView;
    }
}
