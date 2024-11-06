package persistence;

import domain.Cart;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public void addToCart(Cart cart) {
        String checkSql = "SELECT * FROM cart WHERE user_id = ? AND item_id = ?";
        String insertSql = "INSERT INTO cart (user_id, item_id, price) VALUES (?, ?, ?)";

        try {
            Connection conn = DBUtil.getConnection();
            // Check if the item already exists in the cart
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, cart.getUserId());
            checkStmt.setInt(2, cart.getItemId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("您已添加该商品进购物车,请勿重复添加");
            } else {
                // If not, insert new item
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, cart.getUserId());
                insertStmt.setInt(2, cart.getItemId());
//                insertStmt.setInt(3, cart.getQuantity());
//                insertStmt.setDouble(4, cart.getAmount());
                insertStmt.setDouble(3, cart.getPrice());
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
    public List<Cart> getCartItemsByUserId(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE user_id = ?";

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
                        rs.getDouble("price")
                );
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

    //获取单个购物车项的方法
    public Cart getCartItem(int userId, int itemId) {
        String sql = "SELECT * FROM cart WHERE user_id = ? AND item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cart(userId, itemId, rs.getInt("quantity"), rs.getDouble("amount"), rs.getDouble("price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 更新购物车商品数量
    public void updateCartItem(int userId, int itemId, int quantityChange) {
        String sql = "UPDATE cart SET quantity = quantity + ? WHERE user_id = ? AND item_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, quantityChange);
            ps.setInt(2, userId);
            ps.setInt(3, itemId);
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
//            preparedStatement.setInt(3, cart.getQuantity());
//            preparedStatement.setDouble(4, cart.getAmount());
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
        String sql = "DELETE FROM cart WHERE user_id = ? AND item_id = ?";
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

