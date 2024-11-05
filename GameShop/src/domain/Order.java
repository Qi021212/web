package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private double total;//总价
    private int amount;// 商品总数
    private int status;//1未付款/2已付款/3已发货/4已完成
    private int paytype;//1微信/2支付宝/3货到付款
    private String name;
    private String phone;
    private String address;
    private Date datetime;
    private User user;
//    private Map<Integer,OrderItem> itemMap = new HashMap<Integer,OrderItem>();
    private List<OrderItem> itemList = new ArrayList<OrderItem>();

    public Order() { }
    public Order(int id){
        this.id = id;
    }
    // 添加商品到订单



    public void addOrderItem(OrderItem item) {
        itemList.add(item);
        this.amount += item.getAmount();
        this.total += item.getPrice() * item.getAmount();
    }

    public void setUsername(String username) {
        user = new User();
        user.setUsername(username);
    }
//    public void addGoods(Item g) {
//        if(itemMap.containsKey(g.getId())) {
//            OrderItem item = itemMap.get(g.getId());
//            item.setAmount(item.getAmount()+1);
//        }else {
//            OrderItem item = new OrderItem(g.getPrice(),1,g,this);
//            itemMap.put(g.getId(), item);
//        }
//        amount++;
//        total = PriceUtils.add(total, g.getPrice());
//    }

//    public List<OrderItem> getItemList() {
//        return itemList;
//    }
//
//    public void setItemList(List<OrderItem> itemList) {
//        this.itemList = itemList;
//    }

//    public void lessen(int itemId) {
//        if(itemMap.containsKey(itemId)) {
//            OrderItem item = itemMap.get(itemId);
//            item.setAmount(item.getAmount()-1);
//            amount--;
//            total = PriceUtils.subtract(total, item.getPrice());
//            if(item.getAmount()<=0) {
//                itemMap.remove(itemId);
//            }
//        }
//    }
//    public void delete(int itemId) {
//        if(itemMap.containsKey(itemId)) {
//            OrderItem item = itemMap.get(itemId);
//            total = PriceUtils.subtract(total, item.getAmount()*item.getPrice());
//            amount-=item.getAmount();
//            itemMap.remove(itemId);
//        }
//    }

//    public Map<Integer, OrderItem> getItemMap() {
//        return itemMap;
//    }
//
//    public void setItemMap(Map<Integer, OrderItem> itemMap) {
//        this.itemMap = itemMap;
//    }

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
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
//    public Order() {
//        super();
//    }
}