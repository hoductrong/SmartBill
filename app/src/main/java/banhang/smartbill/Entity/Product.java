package banhang.smartbill.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by KARATA on 28/11/2017.
 */

public class Product extends BaseEntity {
    private String Name ;
    private String ProductCode;
    private long UnitPrice ;
    private long InputPrice;
    private String UnitName;
    private Date StartDate;
    private Date EndDate ;
    private String ProductCategoryId ;
    private List<String> ImportProduct;
    private List<String> InformationExtend ;
    private ProductCategory ProductCategory;
    private List<OrderProduct> OrderProduct;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public long getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        UnitPrice = unitPrice;
    }

    public long getInputPrice() {
        return InputPrice;
    }

    public void setInputPrice(long inputPrice) {
        InputPrice = inputPrice;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public String getProductCategoryId() {
        return ProductCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        ProductCategoryId = productCategoryId;
    }

    public List<String> getImportProduct() {
        return ImportProduct;
    }

    public void setImportProduct(List<String> importProduct) {
        ImportProduct = importProduct;
    }

    public List<String> getInformationExtend() {
        return InformationExtend;
    }

    public void setInformationExtend(List<String> informationExtend) {
        InformationExtend = informationExtend;
    }

    public banhang.smartbill.Entity.ProductCategory getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(banhang.smartbill.Entity.ProductCategory productCategory) {
        ProductCategory = productCategory;
    }

    public List<banhang.smartbill.Entity.OrderProduct> getOrderProduct() {
        return OrderProduct;
    }

    public void setOrderProduct(List<banhang.smartbill.Entity.OrderProduct> orderProduct) {
        OrderProduct = orderProduct;
    }


}
