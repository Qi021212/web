package service;

import domain.Item;
import domain.Order;
import domain.OrderItem;
import persistence.impl.CartDAOImpl;
import persistence.impl.OrderDAOImpl;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class OrderService {
    private OrderDAOImpl oDao = new OrderDAOImpl();
    CartDAOImpl cartDao = new CartDAOImpl();

    // 获取商品信息的方法
    public Item getItemById(int itemId) {
        Item item = null;
        String sql = "SELECT * FROM item WHERE id = ?"; // 假设表名为 items

        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                // 根据 Item 类的属性添加更多字段
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    // 添加订单
    public int addOrder(Order order) {
        try {
            return oDao.insertOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加订单失败", e);
        }
    }

    // 添加订单项
    public void addOrderItem(OrderItem orderItem) {
        try {
            oDao.insertOrderItem(orderItem);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加订单项失败", e);
        }
    }

    public void addOrderItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            // 假设有一个 OrderItemDAO 类用于操作数据库
            oDao.addOrderItem(orderItem);
        }
    }

    // 获取订单项列表
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return oDao.getOrderItemsByOrderId(orderId);
    }

    public List<Order> selectAll(int userId) {
        return oDao.findOrdersByUserId(userId);
    }

    public void updateStatus(int id,int status) {
        try {
            oDao.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);

            oDao.deleteOrderItem(con, id);
            oDao.deleteOrder(con, id);
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(con!=null) {
                try {
                    con.rollback();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }finally{
            if(con != null) {
                try{
                    con.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
