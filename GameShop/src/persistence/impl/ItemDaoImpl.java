package persistence.impl;

import domain.Category;
import domain.Item;
import persistence.ItemDao;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    private static final String GET_ITEM_BY_CATEGORY="select * from item where category=?";
    private static final String GET_ALL_ITEM="select * from item";
    private static final String SEARCH_ITEM_LIST="select * from item where name like? or type like?";
    private static final String GET_ITEM_BY_PICTURE_SRC="select * from item where picture=?";




    @Override
    public List<Item> getItemByCategory(String categoryId) {
        List<Item> items=new ArrayList<Item>();
        try {
            Connection connection= DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(GET_ITEM_BY_CATEGORY);
            preparedStatement.setString(1,categoryId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                items.add(this.resultSetToItem(resultSet));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);

        }catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> getAllItem() {
        List<Item> itemList=new ArrayList<>();
        try{
            Connection connection= DBUtil.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(GET_ALL_ITEM);
            while(resultSet.next()){
                itemList.add(this.resultSetToItem(resultSet));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);

        }catch (Exception e){
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public List<Item> searchItemList(String keyword) {
        List<Item> items=new ArrayList<>();
        try {
            Connection connection= DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SEARCH_ITEM_LIST);
            preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,keyword);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                items.add(this.resultSetToItem(resultSet));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);

        }catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> getItemBySrc(String src)
    {
        List<Item> items=new ArrayList<Item>();
        try {
            Connection connection= DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(GET_ITEM_BY_PICTURE_SRC);
            preparedStatement.setString(1,src);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                items.add(this.resultSetToItem(resultSet));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);

        }catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }


    public Item resultSetToItem(ResultSet resultSet) throws SQLException {
        Item item=new Item();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setCategory(resultSet.getString("category"));
        item.setType(resultSet.getString("type"));
        item.setPicture(resultSet.getString("picture"));
        item.setDescription(resultSet.getString("description"));
        item.setPrice(resultSet.getDouble("price"));
        return item;
    }

    //Dongenqie
    @Override
    public List<Item> getAllItems() {
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
    @Override
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
    @Override
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

    @Override
    public Item getItemById(int id) {
        Item item = null;
        String sql = "SELECT * FROM item WHERE id = ?";
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setType(rs.getString("type"));
                item.setPicture(rs.getString("picture"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(stmt);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
}
