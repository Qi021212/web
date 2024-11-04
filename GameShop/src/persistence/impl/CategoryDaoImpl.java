package persistence.impl;

import domain.Category;
import domain.Item;
import persistence.CategoryDao;
import persistence.ItemDao;
import utils.DBUtil;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CategoryDaoImpl implements CategoryDao {

    private static final String GET_CATEGORY_LIST = "SELECT * FROM category";
    private static final String GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE categoryId=?";

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList=new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_CATEGORY_LIST);
            while (resultSet.next()){
                categoryList.add(resultSetToCategory(resultSet));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);

        }catch (Exception e){
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public Category getCategoryById(String categoryId) {
        Category category=null;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORY_BY_ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                category=new Category();
                category.setCategoryId(resultSet.getString("categoryId"));
                category.setName(resultSet.getString("name"));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return category;
    }

    public Category resultSetToCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setCategoryId(resultSet.getString("categoryId"));
        category.setName(resultSet.getString("name"));
        return category;
    }

}
