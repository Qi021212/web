package dao;

import model.Item;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//商品浏览界面：显示所有商品
public class ItemDAO {

    public static List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        // JDBC code to fetch items from database
        // Use DBUtil for getting connection
        String sql = "SELECT * FROM item";
        try {
            Connection connection = DBUtil.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
//                Item item = this.resultSetToItem(resultSet);
                item.setId(resultSet.getInt("id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getDouble("price"));
                item.setDescription(resultSet.getString("description"));
                items.add(item);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
//            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    // 插入商品
    public void insertItem(Item item) {
        String sql = "INSERT INTO item (name, price, description) VALUES (?, ?, ?)";
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.setString(3, item.getDescription());
            preparedStatement.executeUpdate();

            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除商品
    public void deleteItem(int itemId) {
        String sql = "DELETE FROM item WHERE id = ?";
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, itemId);
            preparedStatement.executeUpdate();

            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Item resultSetToItem(ResultSet resultSet) throws Exception {
        Item item = new Item();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setPrice(resultSet.getDouble("price"));
        item.setDescription(resultSet.getString("description"));
        return item;
    }
}

