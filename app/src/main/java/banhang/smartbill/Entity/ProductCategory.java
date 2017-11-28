package banhang.smartbill.Entity;

import java.util.List;

/**
 * Created by KARATA on 28/11/2017.
 */

public class ProductCategory extends BaseEntity {
    private String Name;
    private String IdentityUserId;
    private IdentityUser IdentityUser;
    private List<Product> Product;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdentityUserId() {
        return IdentityUserId;
    }

    public void setIdentityUserId(String identityUserId) {
        IdentityUserId = identityUserId;
    }

    public IdentityUser getIdentityUser() {
        return IdentityUser;
    }

    public void setIdentityUser(IdentityUser identityUser) {
        IdentityUser = identityUser;
    }

    public List<banhang.smartbill.Entity.Product> getProduct() {
        return Product;
    }

    public void setProduct(List<banhang.smartbill.Entity.Product> product) {
        Product = product;
    }
}
