package banhang.smartbill.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Entity.Customer;
import banhang.smartbill.R;

/**
 * Created by KARATA on 05/12/2017.
 * Hiển thị danh sách khách hàng để tạo hóa đơn
 */

public class CustomerListAdapter extends ArrayAdapter<Customer> {
    private List<Customer> customerList;
    private List<Customer> filterList;
    private int layout;
    public CustomerListAdapter(@NonNull Context context, int resource,List<Customer> customerList) {
        super(context,resource);
        this.customerList = customerList;
        this.layout = resource;
        filterList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Nullable
    @Override
    public Customer getItem(int position) {
        Customer customer = filterList.get(position);
        return customer;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Customer)resultValue).getName();
            }
            //lọc các item thỏa mãn điều kiện
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                List<Customer> customerSuggestion = new ArrayList<>();
                if(charSequence != null){
                    for(Customer c : customerList){
                        if(c.getName().toLowerCase().
                                startsWith(charSequence.toString().toLowerCase())){
                            customerSuggestion.add(c);
                        }
                    }
                    filterResults.count = customerSuggestion.size();
                    filterResults.values = customerSuggestion;
                }
                return filterResults;
            }
            //sau khi lọc xong sẽ tạo ra một danh sách list item để được hiển thị
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList.clear();
                if(filterResults != null && filterResults.count > 0){
                    for(Object o : (List<?>)filterResults.values){
                        if(o instanceof Customer){
                            filterList.add((Customer)o);
                        }
                    }
                    notifyDataSetChanged();
                }else if(filterResults == null){
                    filterList.addAll(customerList);
                    notifyDataSetChanged();
                }
            }
        };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.layout,null);
        }
        TextView tv_customer_name = convertView.findViewById(R.id.tv_customer_name);
        tv_customer_name.setText(getItem(position).getName());
        return convertView;
    }

    //find customer by name
    public Customer find(String customerName){
        for(Customer c : customerList){
            if(c.getName().toLowerCase().equals(customerName.toLowerCase())){
                return c;
            }
        }
        return null;
    }
}
