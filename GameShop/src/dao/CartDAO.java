package dao;

import model.Cart;
import model.Item;
import utils.DBUtil;

import java.sql.*;

public class CartDAO {

    public void addToCart(int userId, int itemId, int quantity, double price) {
        String sql = "INSERT INTO cart (user_id, item_id, quantity, amount, price) VALUES (?, ?, ?, ?, ?)";
        double amount = quantity * price; // Calculate total amount
        try {
            Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, itemId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, amount);
            preparedStatement.setDouble(5, price);
            preparedStatement.executeUpdate();

            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void addToCart(int userId, int itemId, int quantity,double price) {
//        String sql = "INSERT INTO cart (user_id, item_id, quantity, amount, price) VALUES (?, ?, ?, ?, ?)";
//        String selectSql = "SELECT price FROM item WHERE id = ?";
//        double itemPrice = 0;
//
//        // 获取商品的价格
//        try {
//            Connection conn = DBUtil.getConnection();
//            PreparedStatement selectPs = conn.prepareStatement(selectSql);
//
//            selectPs.setInt(1, itemId);
//            ResultSet resultSet = selectPs.executeQuery();
//            if (resultSet.next()) {
//                itemPrice = resultSet.getDouble("item");
//            }
//            DBUtil.closeResultSet(resultSet);
//            DBUtil.closePreparedStatement(selectPs);
//            DBUtil.closeConnection(conn);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 计算金额
//        double totalPrice = itemPrice * quantity;
//
//        // 插入到购物车
//        try {
//            Connection conn = DBUtil.getConnection();
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//            ps.setInt(1, userId);
//            ps.setInt(2, itemId);
//            ps.setInt(3, quantity);
//            ps.setInt(4, quantity); // amount = quantity
//            ps.setDouble(5, totalPrice); // price = total price
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

