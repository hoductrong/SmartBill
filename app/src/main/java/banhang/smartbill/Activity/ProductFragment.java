package banhang.smartbill.Activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Adapter.ProductAdapter;
import banhang.smartbill.DAL.ProductAPI;
import banhang.smartbill.DAL.TokenAPI;
import banhang.smartbill.Entity.Product;
import banhang.smartbill.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {
    ArrayList<Product> arrProduct,temp;
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

        product = new ProductAPI();
        runnableUI = new Runnable() {
            @Override
            public void run() {
            adapter.notifyDataSetChanged();
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TokenAPI tokenApi = new TokenAPI();
                tokenApi.getToken("dinhhongphi","phi123");
                arrProduct.clear();
                arrProduct = product.getProducts();
                handlerPost.post(runnableUI);

            }
        });
        thread.start();

        // Inflate the layout for this fragment
        return mView;

    }

    public void initVariable(){
        lvProduct = (ListView)mView.findViewById(R.id.lv_product);

        //svSearch = (SearchView)mView.findViewById(R.id.sv_search);
        arrProduct = new ArrayList<Product>();
        adapter = new ProductAdapter(getActivity(),R.layout.product_listview_custom, arrProduct);
        lvProduct.setAdapter(adapter);


    }


}
