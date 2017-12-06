package banhang.smartbill.Entity;

import java.util.List;

/**
 * Created by KARATA on 06/12/2017.
 * Quản lý hóa đơn hiện tại, Tại một thời điểm chỉ tồn tại một CurrentOrder
 */

public class CurrentOrder {
    private static int Count = 0;
    private Order order;
    private Customer customer;
    private List<OrderProduct> orderProducts;

    public CurrentOrder(Order order,Customer customer)  throws OverloadObjectException{
        if(Count > 0)
            throw new OverloadObjectException();
        if(order != null && customer != null){
            Count++;
            this.order = order;
            this.customer = customer;
        }else{
            throw new IllegalArgumentException("parameter must have value not null");
        }
    }

    public Order get(){
        return order;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Count--;
    }

    public Customer getCustomer(){
        return customer;
    }
}
