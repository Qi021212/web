package persistence.impl;

import domain.User;
import persistence.UserDao;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String INSERT_USER =
            "INSERT INTO user (username,password,email) VALUES (?,?,?)";
    private static final String FIND_USER = "SELECT * FROM user WHERE id = ?";
    private static final String FIND_USER_BY_USERNAME = "SELECT * FROM user WHERE username = ? ";
    private static final String FIND_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM user WHERE username = ? AND password = ?";
    private static final String FIND_ALL_USERS = "SELECT * FROM user";
    private static final String UPDATE_USERNAME_AND_PASSWORD = "UPDATE user SET username = ? , password=? WHERE id=?";

    public List<User> findAllUsers() {
        List<User> userList = new ArrayList<User>();
        try{
            Connection connection = DBUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
            while (resultSet.next()) {
                User user = this.resultSetToUser(resultSet);
                userList.add(user);
            }
            DBUtil.closeStatement(statement);   //最后使用的先关闭
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeConnection(connection);
        }catch(Exception e){
            e.printStackTrace();
        }
        return userList;
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        User result = null;
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_USERNAME_AND_PASSWORD);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                result = this.resultSetToUser(resultSet);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public User findUserById(int id) {
        User result = null;
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                result = this.resultSetToUser(resultSet);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public boolean findUserByName(String username) {
        boolean result = false;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_USERNAME);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                result = true;
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean insertUser(User user) {
        boolean result = false;
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            int rows = preparedStatement.executeUpdate();
            if(rows == 1) {
                result = true;
            }
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private User resultSetToUser(ResultSet resultSet) throws Exception {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
    @Override
    public boolean updateUsernameAndPassword(int id, String username, String password) {
        boolean result = false;
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERNAME_AND_PASSWORD);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, id);
            int rows = preparedStatement.executeUpdate();
            if(rows == 1) {
                result = true;
            }
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();
        System.out.println(connection);
        UserDaoImpl userDao = new UserDaoImpl();
        User user = new User();

        user.setUsername("1");
        user.setPassword("1");
        user.setEmail("1.com");
        userDao.insertUser(user);
        final List<User> allUsers = userDao.findAllUsers();
        for (User u : allUsers) {
            System.out.println(u.toString());
        }
    }
}
