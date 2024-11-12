package persistence.impl;

import domain.Action;
import persistence.ActionDao;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActionDaoImpl implements ActionDao {
    private static final String GET_ACTION_LIST_BY_USER_ID = "SELECT * FROM action WHERE userId=?";
    private static final String INSERT_ACTION_LIST_BY_USER_ID = "INSERT INTO action (userId,itemName,type) VALUES (?,?,?)";
    @Override
    public List<Action> getActionListByUserId(int userId) {
        List<Action> actions = new ArrayList<>();
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ACTION_LIST_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                actions.add(resultSetToAction(resultSet));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actions;
    }

    @Override
    public void insertAction(Action action) {
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACTION_LIST_BY_USER_ID);
            preparedStatement.setInt(1, action.getUserId());
            preparedStatement.setString(2, action.getItemName());
            preparedStatement.setString(3, action.getType());
            preparedStatement.executeUpdate();
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Action resultSetToAction(ResultSet resultSet) throws SQLException {
        Action action=new Action();
        action.setUserId(resultSet.getInt("userId"));
        action.setItemName(resultSet.getString("itemName"));
        action.setType(resultSet.getString("type"));
        return action;
    }
}
