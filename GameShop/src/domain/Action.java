package domain;

import java.util.Date;

public class Action {
    private int userId;
    private String itemName;
    private String type;


    public Action() {
    }



    public Action(int userId, String itemName, String type) {
        this.userId = userId;
        this.itemName = itemName;
        this.type = type;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
