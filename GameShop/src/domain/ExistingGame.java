package domain;

public class ExistingGame {
    private int id;
    private int userId;
    private int itemId;
    private String itemName;

    public ExistingGame(int id, int userId, int itemId, String itemName) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
