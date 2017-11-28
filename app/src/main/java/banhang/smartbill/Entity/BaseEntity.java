package banhang.smartbill.Entity;

/**
 * Created by KARATA on 28/11/2017.
 */

public class BaseEntity {
    private String Id;
    private boolean IsDelete;
    private String Description;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
