package banhang.smartbill.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by KARATA on 28/11/2017.
 */

public class Order extends BaseEntity {
    private Date CreateDate;
    private boolean Paid;
    private String CustomerId;
    private String BillId;
    private String IdentityUserId;
    private String InterestRateId ;
    private Customer Customer;
    private List<String> Bill;
    private  List<IdentityUser> IdentityUser;
    private  List<String> InterestRate;
    private List<OrderProduct> OrderProduct;

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public boolean isPaid() {
        return Paid;
    }

    public void setPaid(boolean paid) {
        Paid = paid;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getBillId() {
        return BillId;
    }

    public void setBillId(String billId) {
        BillId = billId;
    }

    public String getIdentityUserId() {
        return IdentityUserId;
    }

    public void setIdentityUserId(String identityUserId) {
        IdentityUserId = identityUserId;
    }

    public String getInterestRateId() {
        return InterestRateId;
    }

    public void setInterestRateId(String interestRateId) {
        InterestRateId = interestRateId;
    }

    public Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(Customer customer) {
        Customer = customer;
    }

    public List<String> getBill() {
        return Bill;
    }

    public void setBill(List<String> bill) {
        Bill = bill;
    }

    public List<IdentityUser> getIdentityUser() {
        return IdentityUser;
    }

    public void setIdentityUser(List<IdentityUser> identityUser) {
        IdentityUser = identityUser;
    }

    public List<String> getInterestRate() {
        return InterestRate;
    }

    public void setInterestRate(List<String> interestRate) {
        InterestRate = interestRate;
    }

    public List<OrderProduct> getOrderProduct() {
        return OrderProduct;
    }

    public void setOrderProduct(List<OrderProduct> orderProduct) {
        OrderProduct = orderProduct;
    }


}
