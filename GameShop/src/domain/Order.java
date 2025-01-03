package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private double total;//总价
    private String name;
    private String phone;
    private String email;
    private int paytype;//1微信/2支付宝/3货到付款
    private int status;//1未付款/2已付款/3已发货/4已完成
    private Date datetime;
    private List<OrderItem> itemList;

    private User user;

    public Order() { }
    public Order(int id){
        this.id = id;
    }

    public Order(int userId, String name, String phone, String email, double total, int paytype) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.total = total;
        this.paytype = paytype;
    }

    // 添加商品到订单
    public void addOrderItem(OrderItem item) {
        itemList.add(item);
        this.total += item.getPrice() * item.getAmount();
    }

    public Order(int id, double total, String name, String phone, String email, int paytype, int status, Date datetime) {
        this.id = id;
        this.total = total;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.paytype = paytype;
        this.status = status;
        this.datetime = datetime;
    }

    public void setUsername(String username) {
        user = new User();
        user.setUsername(username);
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

    public List<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getPaytype() {
        return paytype;
    }
    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Date getDatetime() {
        return datetime;
    }
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

}
