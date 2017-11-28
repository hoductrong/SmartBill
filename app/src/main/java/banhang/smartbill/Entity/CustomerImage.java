package banhang.smartbill.Entity;

/**
 * Created by KARATA on 28/11/2017.
 */

public class CustomerImage extends BaseEntity {
    public byte[] LargeImage;
    public Customer Customer;

    public byte[] getLargeImage() {
        return LargeImage;
    }

    public void setLargeImage(byte[] largeImage) {
        LargeImage = largeImage;
    }

    public banhang.smartbill.Entity.Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(banhang.smartbill.Entity.Customer customer) {
        Customer = customer;
    }


}
