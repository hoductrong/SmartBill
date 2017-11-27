package banhang.smartbill.Entity;

import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.R;

/**
 * Created by KARATA on 25/11/2017.
 */

public class MenuEntity {
    public static final int ORDER_LIST_ITEM = 0;
    public static final int PRODUCT_LIST_ITEM = 1;
    public static final int APPLICATION_INFO_ITEM = 2;
    public static final int SIGNOUT_ITEM = 3;

    public static List<NavigationMenuEntity> getMenuItemList(){
        List<NavigationMenuEntity> list = new ArrayList<>();
        list.add(new NavigationMenuEntity(R.drawable.order_icon,"Danh sách hóa đơn"));
        list.add(new NavigationMenuEntity(R.drawable.product_icon,"Sản phẩm"));
        list.add(new NavigationMenuEntity(R.drawable.info_icon,"Thông tin ứng dụng"));
        list.add(new NavigationMenuEntity(R.drawable.signout_icon, "Đăng xuất"));
        return list;
    }
}