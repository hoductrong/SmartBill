package banhang.smartbill.Entity;

/**
 * Created by KARATA on 28/11/2017.
 */

public class OrderProduct extends BaseEntity {
    private float Amount;
    private Product Product;
    private Order Order;
    private String ProductId;
    private String OrderId;

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
        Product = product;
    }

    public banhang.smartbill.Entity.Order getOrder() {
        return Order;
    }

    public void setOrder(banhang.smartbill.Entity.Order order) {
        Order = order;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }


}
