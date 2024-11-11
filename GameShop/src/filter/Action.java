package filter;

public class Action {
    private int userId;
    private int itemId;
    private String type;

    public Action() {
    }

    public Action(int userId, int itemId, String type) {
        this.userId = userId;
        this.itemId = itemId;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
