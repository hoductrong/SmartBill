package banhang.smartbill.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Activity.MainActivity;
import banhang.smartbill.Adapter.ProductAdapter;
import banhang.smartbill.DAL.OrdersAPI;
import banhang.smartbill.DAL.ProductAPI;
import banhang.smartbill.DAL.TokenAPI;
import banhang.smartbill.Entity.Order;
import banhang.smartbill.Entity.Product;
import banhang.smartbill.Entity.UnauthorizedAccessException;
import banhang.smartbill.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {
    List<Product> arrProduct,temp;
    ProductAdapter adapter;
    ListView lvProduct = null;
    SearchView svSearch;
    View mView;
    Runnable runnableUI;
    ProductAPI product;

    Handler handlerPost = new Handler();
    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = inflater.inflate(R.layout.product_fragment, container, false);
        initVariable();
        getAndShowProducts();

        // Inflate the layout for this fragment
        return mView;

    }

    public void initVariable(){
        lvProduct = (ListView)mView.findViewById(R.id.lv_product);

        svSearch = (SearchView)mView.findViewById(R.id.sv_search);
        svSearch.setQueryHint("Search View");
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.toString());

                adapter.notifyDataSetChanged();
                return true;
            }
        });

        arrProduct = new ArrayList<Product>();
        adapter = new ProductAdapter(getActivity(),R.layout.product_listview_custom, arrProduct);
        lvProduct.setAdapter(adapter);


    }
    private void getAndShowProducts(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1:
                        arrProduct.clear();
                        arrProduct.addAll((ArrayList<Product>)msg.obj);
                        adapter.notifyDataSetChanged();
                        break;
                    case 2 : //error unauthorize
                        Toast.makeText(getContext(),R.string.unauthorize,Toast.LENGTH_LONG).show();
                        MainActivity.requireLogin(getContext());
                        break;

                }
            }
        };

        Thread getProductThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ProductAPI api = new ProductAPI();
                    List<Product> products = api.getProducts();
                    Message message = handler.obtainMessage(1,products);
                    handler.sendMessage(message);
                }catch(UnauthorizedAccessException ex){
                    Message message = handler.obtainMessage(2,"Unauthorize");
                    handler.sendMessage(message);
                }
            }
        });
        getProductThread.start();
    }

}
