package domain;

public class Cart {
    private int id;
    private int userId;
    private int itemId;
    private String name;
    private int quantity;
    private double amount;
    private double price;

    public Cart() {}

    public Cart(int id, int userId, int itemId, int quantity, double amount, double price) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.amount = amount;
        this.price = price;
    }

    public Cart(int userId, int itemId, int quantity, double amount, double price) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.amount = amount;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}