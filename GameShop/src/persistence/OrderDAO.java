package persistence;

import domain.Order;
import domain.OrderItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    public int insertOrder(Order order);
    public void insertOrderItem(OrderItem orderItem);
    public List<Order> selectAll(int userId);
    public List<OrderItem> selectOrderItems(int orderId);
    public List<OrderItem> selectAllItem(int orderId);
    public void updateStatus(int orderId, int status);
    public void deleteOrder(Connection con, int orderId);
    public void deleteOrderItem(Connection con, int orderId);
    public int getOrderCount(int status);
    public List<Order> selectOrderList(int status, int pageNumber, int pageSize);
    public List<Order> findOrdersByUserId(int userId);
    public List<OrderItem> getOrderItemsByOrderId(int orderId);
}
