package persistence.impl;

import domain.Item;
import domain.Order;
import domain.OrderItem;
import persistence.OrderDAO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    //插入订单
    public int insertOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, total, name, phone, email, paytype, status, datetime) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, order.getUserId());
            pstmt.setDouble(2, order.getTotal());
            pstmt.setString(3, order.getName());
            pstmt.setString(4, order.getPhone());
            pstmt.setString(5, order.getEmail());
            pstmt.setInt(6, order.getPaytype());
            pstmt.setInt(7, order.getStatus());
            pstmt.setTimestamp(8, new Timestamp(order.getDatetime().getTime()));

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  // 返回订单ID
            }

            DBUtil.closeResultSet(pstmt.getResultSet());
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //插入订单项
    public void insertOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, item_id, price) VALUES (?, ?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, orderItem.getOrder().getId());
            pstmt.setInt(2, orderItem.getItem().getId());
            pstmt.setDouble(3, orderItem.getPrice());
            pstmt.executeUpdate();
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> selectAll(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id")); // Assuming User object is created like this
                order.setTotal(rs.getFloat("total"));
//                order.setAmount(rs.getInt("amount"));
                order.setStatus(rs.getInt("status"));
                order.setPaytype(rs.getInt("paytype"));
                order.setName(rs.getString("name"));
                order.setPhone(rs.getString("phone"));
//                order.setAddress(rs.getString("address"));
                order.setDatetime(rs.getTimestamp("datetime"));
                orders.add(order);
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    // 获取订单项
    public List<OrderItem> selectOrderItems(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderId(orderId);
                item.setItemId(rs.getInt("item_id"));
                item.setPrice(rs.getDouble("price"));
                item.setAmount(rs.getInt("amount"));
                orderItems.add(item);
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public List<OrderItem> selectAllItem(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setOrder(new Order(orderId)); // Assuming Order object is created like this
                item.setItem(new Item(rs.getInt("item_id"))); // Assuming Item object is created like this
                item.setPrice(rs.getDouble("price"));
                item.setAmount(rs.getInt("amount"));
                orderItems.add(item);
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public void updateStatus(int orderId, int status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, status);
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();

            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(Connection con, int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try  {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(con);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderItem(Connection con, int orderId) {
        String sql = "DELETE FROM order_items WHERE order_id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(con);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getOrderCount(int status) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM orders WHERE status = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, status);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Order> selectOrderList(int status, int pageNumber, int pageSize) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ? LIMIT ?, ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, status);
            pstmt.setInt(2, (pageNumber - 1) * pageSize);
            pstmt.setInt(3, pageSize);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTotal(rs.getFloat("total"));
//                order.setAmount(rs.getInt("amount"));
                order.setStatus(rs.getInt("status"));
                order.setPaytype(rs.getInt("paytype"));
                order.setDatetime(rs.getTimestamp("datetime"));
                orders.add(order);
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> findOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        // 这里添加你的数据库查询逻辑
        // 假设使用JDBC进行查询
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTotal(rs.getDouble("total"));
                order.setName(rs.getString("name"));
                order.setPhone(rs.getString("phone"));
                order.setEmail(rs.getString("email"));
                order.setPaytype(rs.getInt("paytype"));
                order.setStatus(rs.getInt("status"));
                order.setDatetime(rs.getDate("datetime"));
                // 还可以加入itemList
                orders.add(order);
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    // 获取指定订单的订单项列表
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT oi.id, oi.order_id, oi.item_id, oi.price, i.name AS item_name " +
                "FROM order_items oi JOIN item i ON oi.item_id = i.id WHERE oi.order_id = ?";

        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));
                orderItem.setOrderId(rs.getInt("order_id"));
                orderItem.setItemId(rs.getInt("item_id"));
                orderItem.setPrice(rs.getDouble("price"));
                orderItem.setItemName(rs.getString("item_name")); // 需要在 OrderItem 类中添加 itemName 字段
                orderItems.add(orderItem);
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(stmt);
            DBUtil.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, item_id, price) VALUES (?, ?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getItemId());
            ps.setDouble(3, orderItem.getPrice());
            ps.executeUpdate();
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
