package banhang.smartbill.Entity;

/**
 * Created by KARATA on 25/11/2017.
 */

public class NavigationMenuEntity {
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NavigationMenuEntity(int icon, String name){
        this.icon = icon;
        this.name = name;
    }

    private int icon;
    private String name;
}
