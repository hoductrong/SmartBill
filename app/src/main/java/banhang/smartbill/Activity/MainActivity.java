package banhang.smartbill.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import banhang.smartbill.Adapter.NavigationMenuAdapter;
import banhang.smartbill.DAL.TokenAPI;
import banhang.smartbill.Entity.CurrentOrder;
import banhang.smartbill.Entity.MenuEntity;
import banhang.smartbill.Fragment.OrderDetailFragment;
import banhang.smartbill.Fragment.OrderFragment;
import banhang.smartbill.Fragment.ProductFragment;
import banhang.smartbill.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    public static CurrentOrder CurrentOrder = null; //lưu trữ hóa đơn đang được xử lý

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

        //start main fragment
        showFragment(new OrderFragment());
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
                case MenuEntity.APPLICATION_INFO_ITEM : fragment = new OrderDetailFragment();
                    break;
                case MenuEntity.ORDER_LIST_ITEM:
                    fragment = new OrderFragment();
                    break;
                case MenuEntity.PRODUCT_LIST_ITEM : fragment = new ProductFragment();
                    break;
                case MenuEntity.SIGNOUT_ITEM:
                    MainActivity.requireLogin(MainActivity.this);
                    break;
                default:
            }

            showFragment(fragment);
        }
    }
    //show fragment
    public void showFragment(Fragment fragment){
        if (fragment != null) {
            //replace fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            if(fragment instanceof OrderFragment){
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).
                        addToBackStack("main").commit();
            }else{
                fragmentManager.popBackStackImmediate("second",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).
                        addToBackStack("second").commit();
            }
            //close toggle menu
            if(mDrawerLayout.isDrawerOpen(Gravity.RIGHT)){
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    ///show login form to allow user authorization
    public static void requireLogin(Context context){
        //reset token
        TokenAPI.TOKEN = null;
        SharedPreferences.Editor editor = context.getSharedPreferences(LoginActivity.MYAPP,MODE_PRIVATE).edit();
        editor.putString(LoginActivity.TOKEN,TokenAPI.TOKEN);
        editor.apply();
        //require new login
        Intent intent = new Intent(context,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int index = fragmentManager.getBackStackEntryCount() - 1;
        //if this is OrderFragment then move main screen
        if(index == 0){
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }else{
            super.onBackPressed();
        }
    }
}
