package banhang.smartbill.Activity;

import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import banhang.smartbill.Adapter.NavigationMenuAdapter;
import banhang.smartbill.DAL.ProductAPI;
import banhang.smartbill.DAL.TokenAPI;
import banhang.smartbill.Entity.GrantTokenResult;
import banhang.smartbill.Entity.MenuEntity;
import banhang.smartbill.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupToolbar();

        //init navigation menu list
        NavigationMenuAdapter adapter = new NavigationMenuAdapter(this,
                R.layout.navigation_item_list,
                MenuEntity.getMenuItemList());
        ListView lsv_menu = (ListView)findViewById(R.id.menu_list);
        lsv_menu.setAdapter(adapter);
        lsv_menu.setOnItemClickListener(new NavigationMenuItemClickListener());

        setupDrawerToggle();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TokenAPI tokenApi = new TokenAPI();
                String token = tokenApi.getToken();
                ProductAPI productApi= new ProductAPI();
                productApi.getProducts();
            }
        });
        thread.start();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //don't show back icon button
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,
                toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();

        //toggle right navigation panel
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDrawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                }else{
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
    }

    ///
    ///show fragment when navigation menu item click
    ///
    private class NavigationMenuItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Fragment fragment = null;
            switch (i){
                case MenuEntity.APPLICATION_INFO_ITEM :
                    break;
                case MenuEntity.ORDER_LIST_ITEM:
                    break;
                case MenuEntity.PRODUCT_LIST_ITEM:
                    break;
                case MenuEntity.SIGNOUT_ITEM:
                    break;
                default:
            }

            if (fragment != null) {
                //replace fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                //close toggle menu
            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        }
    }
}
