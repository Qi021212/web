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
        String insertSql = "INSERT INTO cart (user_id, item_id, quantity, amount, price) VALUES (?, ?, ?, ?, ?)";
        String updateSql = "UPDATE cart SET quantity = ?, amount = ? WHERE user_id = ? AND item_id = ?";

        try {
            Connection conn = DBUtil.getConnection();
            // Check if the item already exists in the cart
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, cart.getUserId());
            checkStmt.setInt(2, cart.getItemId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // If exists, update the quantity and amount
                int newQuantity = rs.getInt("quantity") + cart.getQuantity();
                double newAmount = newQuantity * cart.getPrice();
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, newQuantity);
                updateStmt.setDouble(2, newAmount);
                updateStmt.setInt(3, cart.getUserId());
                updateStmt.setInt(4, cart.getItemId());
                updateStmt.executeUpdate();
            } else {
                // If not, insert new item
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, cart.getUserId());
                insertStmt.setInt(2, cart.getItemId());
                insertStmt.setInt(3, cart.getQuantity());
                insertStmt.setDouble(4, cart.getAmount());
                insertStmt.setDouble(5, cart.getPrice());
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

    // 获取购物车商品的方法保持不变
    public List<Cart> getCartItemsByUserId(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        String sql = "SELECT c.item_id, c.quantity, c.price, c.amount, i.name " +
                "FROM cart c JOIN item i ON c.item_id = i.id " +
                "WHERE c.user_id = ? AND c.quantity > 0"; // 只查询数量大于0的商品
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart(userId, rs.getInt("item_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("amount"),
                        rs.getDouble("price"));
                cart.setName(rs.getString("name"));
                cartItems.add(cart);
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
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
        String sql = "INSERT INTO cart (user_id, item_id, quantity, amount, price) VALUES (?, ?, ?, ?, ?)";
        int result = 0;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, cart.getUserId());
            preparedStatement.setInt(2, cart.getItemId());
            preparedStatement.setInt(3, cart.getQuantity());
            preparedStatement.setDouble(4, cart.getAmount());
            preparedStatement.setDouble(5, cart.getPrice());
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


//    //插入购物车方法1
//    public void addToCart(Cart cart) {
//        String sql = "INSERT INTO cart (user_id, item_id, quantity, amount, price) VALUES (?, ?, ?, ?, ?)";
//        try {
//            Connection connection = DBUtil.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setInt(1, cart.getUserId());
//            preparedStatement.setInt(2, cart.getItemId());
//            preparedStatement.setInt(3, cart.getQuantity());
//            preparedStatement.setDouble(4, cart.getAmount());
//            preparedStatement.setDouble(5, cart.getPrice());
//            preparedStatement.executeUpdate();
//
//            DBUtil.closePreparedStatement(preparedStatement);
//            DBUtil.closeConnection(connection);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //29日19：40
//    // 插入购物车方法
//    public int addToCart(Cart cart) {
//        String sql = "INSERT INTO cart (user_id, item_id, quantity, amount, price) VALUES (?, ?, ?, ?, ?)";
//        int result = 0;
//        try {
//            Connection connection = DBUtil.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setInt(1, cart.getUserId());
//            preparedStatement.setInt(2, cart.getItemId());
//            preparedStatement.setInt(3, cart.getQuantity());
//            preparedStatement.setDouble(4, cart.getAmount());
//            preparedStatement.setDouble(5, cart.getPrice());
//            result = preparedStatement.executeUpdate();
//
//            DBUtil.closePreparedStatement(preparedStatement);
//            DBUtil.closeConnection(connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public List<Cart> getCartItemsByUserId(int userId) {
//        List<Cart> cartItems = new ArrayList<>();
//        String sql = "SELECT c.item_id, c.quantity, c.price, c.amount, i.name " +
//                "FROM cart c JOIN item i ON c.item_id = i.id " +
//                "WHERE c.user_id = ?";
//        try {
//            Connection conn = DBUtil.getConnection();
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, userId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Cart cart = new Cart(userId, rs.getInt("item_id"),
//                        rs.getInt("quantity"),
//                        rs.getDouble("amount"),
//                        rs.getDouble("price"));
//                cart.setName(rs.getString("name")); // 设置商品名称
//                cartItems.add(cart);
//            }
//            DBUtil.closeResultSet(rs);
//            DBUtil.closePreparedStatement(ps);
//            DBUtil.closeConnection(conn);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return cartItems;
//    }



//    // 获取购物车商品
//    public List<Cart> getCartItemsByUserId(int userId) {
//        List<Cart> cartItems = new ArrayList<>();
//        String sql = "SELECT * FROM cart WHERE user_id = ?";
//
//        try {
//            Connection connection = DBUtil.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, userId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                Cart cart = new Cart();
//                cart.setId(resultSet.getInt("id"));
//                cart.setUserId(resultSet.getInt("user_id"));
//                cart.setItemId(resultSet.getInt("item_id"));
//                cart.setQuantity(resultSet.getInt("quantity"));
//                cart.setAmount(resultSet.getDouble("amount"));
//                cart.setPrice(resultSet.getDouble("price"));
//                cartItems.add(cart);
//            }
//            DBUtil.closeResultSet(resultSet);
//            DBUtil.closePreparedStatement(preparedStatement);
//            DBUtil.closeConnection(connection);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return cartItems;
//    }

//    public List<Cart> getCartItems(int userId) {
//        List<Cart> cartItems = new ArrayList<>();
//        String sql = "SELECT * FROM cart WHERE user_id = ?";
//        try {
//            Connection connection = DBUtil.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setInt(1, userId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int itemId = resultSet.getInt("item_id");
//                int quantity = resultSet.getInt("quantity");
//                double amount = resultSet.getDouble("amount");
//                double price = resultSet.getDouble("price");
//                cartItems.add(new Cart(userId, itemId, quantity, amount, price));
//            }
//            DBUtil.closeResultSet(resultSet);
//            DBUtil.closePreparedStatement(preparedStatement);
//            DBUtil.closeConnection(connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return cartItems;
//    }



//    public void addToCart(int userId, int itemId, int quantity, double price) {
//        String sql = "INSERT INTO cart (user_id, item_id, quantity, amount, price) VALUES (?, ?, ?, ?, ?)";
//        double amount = quantity * price; // Calculate total amount
//        try {
//            Connection connection = DBUtil.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setInt(1, userId);
//            preparedStatement.setInt(2, itemId);
//            preparedStatement.setInt(3, quantity);
//            preparedStatement.setDouble(4, amount);
//            preparedStatement.setDouble(5, price);
//            preparedStatement.executeUpdate();
//
//            DBUtil.closePreparedStatement(preparedStatement);
//            DBUtil.closeConnection(connection);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

