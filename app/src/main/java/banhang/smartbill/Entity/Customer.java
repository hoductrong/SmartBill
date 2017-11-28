package banhang.smartbill.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by KARATA on 28/11/2017.
 */

public class Customer extends BaseEntity {
    private String Name;
    private int Sex;
    private String Identity;
    private Date CreateDate;
    private String Address;
    private String RoomId;
    private String IdentityUserId;
    private String Room;
    private List<String> InformationExtend;
    private CustomerImage CustomerImage;
    private IdentityUser IdentityUser;
    private List<Order> Order;
    private List<String> Bill;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public String getIdentity() {
        return Identity;
    }

    public void setIdentity(String identity) {
        Identity = identity;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getIdentityUserId() {
        return IdentityUserId;
    }

    public void setIdentityUserId(String identityUserId) {
        IdentityUserId = identityUserId;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public List<String> getInformationExtend() {
        return InformationExtend;
    }

    public void setInformationExtend(List<String> informationExtend) {
        InformationExtend = informationExtend;
    }

    public CustomerImage getCustomerImage() {
        return CustomerImage;
    }

    public void setCustomerImage(CustomerImage customerImage) {
        CustomerImage = customerImage;
    }

    public IdentityUser getIdentityUser() {
        return IdentityUser;
    }

    public void setIdentityUser(IdentityUser identityUser) {
        IdentityUser = identityUser;
    }

    public List<banhang.smartbill.Entity.Order> getOrder() {
        return Order;
    }

    public void setOrder(List<banhang.smartbill.Entity.Order> order) {
        Order = order;
    }

    public List<String> getBill() {
        return Bill;
    }

    public void setBill(List<String> bill) {
        Bill = bill;
    }


}
