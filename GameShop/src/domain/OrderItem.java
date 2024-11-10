//package model;
//
//public class OrderItem {
//    private int id;
//    private double price;
//    private int amount;
//    private String itemName;
//    private Item item;
//    private Order order;
//
//    public void setName(String name) {
//        this.itemName=name;
//    }
//
//    public String getItemName() {
//        return itemName;
//    }
//
//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }
//
//    public int getId() {
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
//    public double getPrice() {
//        return price;
//    }
//    public void setPrice(double price) {
//        this.price = price;
//    }
//    public int getAmount() {
//        return amount;
//    }
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }
//    public Item getItem() {
//        return item;
//    }
//    public void setItem(Item item) {
//        this.item = item;
//    }
//    public Order getOrder() {
//        return order;
//    }
//    public void setOrder(Order order) {
//        this.order = order;
//    }
//    public OrderItem() {
//        super();
//    }
//    public OrderItem(double price, int amount, Item item, Order order) {
//        super();
//        this.price = price;
//        this.amount = amount;
//        this.item = item;
//        this.order = order;
//    }
//}

package domain;

public class OrderItem {
    private int id;
    private int orderId; // 确保这里是订单ID
    private Order order; // 关联的订单
    private int itemId;
    private Item item;
    private double price;
    private int amount;
    private String itemName;

    public OrderItem() {}

    public OrderItem(Item item, double price, int amount, Order order) {
        this.item = item;
        this.price = price;
        this.amount = amount;
        this.order = order;
    }

    public OrderItem(int itemId, Item item, double price, Order order) {
        this.itemId = itemId;
        this.item = item;
        this.price = price;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}

