package banhang.smartbill;

/**
 * Created by phuc-kun on 11/24/2017.
 */

public class Hoadon {
    private String Name;
    private  String Ngaylap;
    private String Trangthai;


    public Hoadon(String name,String ngay,String trangthai){
        this.Name = name;
        this.Ngaylap = ngay;
        this.Trangthai = trangthai;
    }
    public void setName(String name){this.Name = name;}
    public String getName(){return this.Name;}
    public void setNgaylap(String ngaylap){this.Ngaylap = ngaylap;}
    public String getNgaylap(){return this.Ngaylap;}
    public void setTrangthai(String trangthai){this.Trangthai = trangthai;}
    public String getTrangthai(){return this.Trangthai;}
}
