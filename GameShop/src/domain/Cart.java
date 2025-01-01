package domain;

public class Cart {
    private int id;
    private int userId;
    private int itemId;
    private String name;
    private String picture;
    private double price;
    private Item item;
    private int inCart;  // 是否在购物车中
    private int addCount;  // 添加次数
    private int isSelected; // 是否在购物车中被选中

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Cart() {}

//    public Cart(int id, int userId, int itemId, int quantity, double amount, double price) {
//        this.id = id;
//        this.userId = userId;
//        this.itemId = itemId;
//        this.price = price;
//    }

    public Cart(int userId, int itemId, int quantity, double totalPrice, double price) {
        this.userId = userId;
        this.itemId = itemId;
        this.addCount = quantity;  // 用 quantity 表示添加次数
        this.price = price;  // 商品单价
        // totalPrice 可以作为计算结果, 若需要保存可以加一个成员变量
    }

    // 全参构造函数
    public Cart(int id, int userId, int itemId, String name, String picture, double price, int inCart, int addCount, int isSelected) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.inCart = inCart;
        this.addCount = addCount;
        this.isSelected = isSelected;
    }

    public Cart(int id, int userId, int itemId, double price, int inCart, int addCount, int isSelected) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
        this.inCart = inCart;
        this.addCount = addCount;
        this.isSelected = isSelected;
    }

    public Cart(int userId, int itemId, double price, int inCart, int addCount) {
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
        this.inCart = inCart;
        this.addCount = addCount;
    }

    public Cart(int id, int userId, int itemId, double price, int inCart, int addCount) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
        this.inCart = inCart;
        this.addCount = addCount;
    }

    public Cart(int userId, int itemId, double price) {
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
    }

    public Cart(int id, int userId, int itemId, double price) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
    }

    public int getIsSelected() {
        return isSelected;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInCart() {
        return inCart;
    }

    public void setInCart(int inCart) {
        this.inCart = inCart;
    }

    public int getAddCount() {
        return addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
