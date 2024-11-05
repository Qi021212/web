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
}
