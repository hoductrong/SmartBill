package banhang.smartbill.Adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Entity.Customer;
import banhang.smartbill.Entity.Product;
import banhang.smartbill.R;

/**
 * Created by MyPC on 04/12/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> implements Filterable {
    private Activity context;
    private List<Product> listProducts,mList;
    public ProductAdapter(Activity context, int layoutID, List<Product> objects) {
        super(context, layoutID, objects);
        this.context = context;
        listProducts = objects;
        mList = new ArrayList<>(objects);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    @Nullable
    @Override
    public Product getItem(int position) {
        Product product = mList.get(position);
        return product;
    }
    @Override
    public Filter getFilter() {
        // return a filter that filters data based on a constraint

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // Create a FilterResults object
                FilterResults results = new FilterResults();

                // If the constraint (search string/pattern) is null
                // or its length is 0, i.e., its empty then
                // we just set the `values` property to the
                // original contacts list which contains all of them
                if (constraint == null || constraint.length() == 0) {
                    results.values = listProducts;
                    results.count = listProducts.size();
                }
                else {
                    // Some search copnstraint has been passed
                    // so let's filter accordingly
                    ArrayList<Product> filteredProducts = new ArrayList<Product>();

                    // We'll go through all the contacts and see
                    // if they contain the supplied string
                    for (Product c : listProducts) {
                        if (c.getName().toUpperCase().contains( constraint.toString().toUpperCase() )) {
                            // if `contains` == true then add it
                            // to our filtered list
                            filteredProducts.add(c);
                        }
                    }
                    // Finally set the filtered values and size/count
                    results.values = filteredProducts;
                    results.count = filteredProducts.size();
                }

                // Return our FilterResults object
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mList.clear();
                mList.addAll( (ArrayList<Product>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_listview_custom, null, false);
        }

        TextView tvProductName = convertView.findViewById(R.id.tv_product_name);
        ImageView ivShoppingcart = convertView.findViewById(R.id.iv_shopping_cart_product);


        if (getItem(position).getName() != null) {
            tvProductName.setText(getItem(position).getName());
        } else tvProductName.setText("");

        return convertView;
    }
}
