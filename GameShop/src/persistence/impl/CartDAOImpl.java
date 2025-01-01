package persistence.impl;

import domain.Cart;
import persistence.CartDAO;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {

    public void addToCart(Cart cart) {
        String checkSql = "SELECT * FROM cart WHERE user_id = ? AND item_id = ?";
        String insertSql = "INSERT INTO cart (user_id, item_id, price, in_cart, add_count) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, cart.getUserId());
            checkStmt.setInt(2, cart.getItemId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("您已添加该商品进购物车,请勿重复添加");
            } else {
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, cart.getUserId());
                insertStmt.setInt(2, cart.getItemId());
                insertStmt.setDouble(3, cart.getPrice());
                insertStmt.setInt(4, cart.getInCart());
                insertStmt.setInt(5, cart.getAddCount());
                insertStmt.executeUpdate();
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(checkStmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 检查商品是否已经在购物车中
    public boolean itemExists(int userId, int itemId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE user_id = ? AND item_id = ?";
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 如果计数大于 0，则表示商品已存在
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 获取用户购物车中的所有商品
    // 在获取购物车商品时，查询 is_selected 字段
    public List<Cart> getCartItemsByUserId(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE user_id = ? AND in_cart = 1"; // 只查询in_cart = 1的商品

        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cart cartItem = new Cart(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("item_id"),
                        rs.getDouble("price"),
                        rs.getInt("in_cart"),
                        rs.getInt("add_count")
                );
                cartItem.setIsSelected(rs.getInt("is_selected")); // 获取is_selected字段
                cartItems.add(cartItem);
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(stmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // 更新购物车商品选中状态
    public void updateCartItemSelection(int userId, int itemId, int isSelected) {
        String sql = "UPDATE cart SET is_selected = ? WHERE user_id = ? AND item_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, isSelected);
            ps.setInt(2, userId);
            ps.setInt(3, itemId);
            ps.executeUpdate();
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取单个购物车项的方法（根据用户ID和商品ID）
    public Cart getCartItem(int userId, int itemId) {
        String sql = "SELECT * FROM cart WHERE user_id = ? AND item_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cart(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("item_id"),
                        rs.getDouble("price"),
                        rs.getInt("in_cart"),
                        rs.getInt("add_count"),
                        rs.getInt("is_selected")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 更新购物车商品数量
    public void updateCartItem(Cart cart) {
        String sql = "UPDATE cart SET in_cart = ?, add_count = ? WHERE user_id = ? AND item_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cart.getInCart());
            ps.setInt(2, cart.getAddCount());
            ps.setInt(3, cart.getUserId());
            ps.setInt(4, cart.getItemId());
            ps.executeUpdate();
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 插入购物车商品
    public int insertCart(Cart cart) {
        String sql = "INSERT INTO cart (user_id, item_id, price) VALUES (?, ?, ?)";
        int result = 0;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, cart.getUserId());
            preparedStatement.setInt(2, cart.getItemId());
            preparedStatement.setDouble(3, cart.getPrice());
            result = preparedStatement.executeUpdate();

            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 删除购物车中的商品
    public void deleteCartItem(int userId, int itemId) {
        String sql = "UPDATE cart SET in_cart = 0 WHERE user_id = ? AND item_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            ps.executeUpdate();
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除用户所有购物车项
    public void deleteAllItemsByUserId(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

