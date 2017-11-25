package banhang.smartbill;

/**
 * Created by MyPC on 25/11/2017.
 */

public class HoaDon {
private String name;
private String price;
private int number;

    public HoaDon(String name, String price,int number){
        this.name=name;
        this.price=price;
        this.number=number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
