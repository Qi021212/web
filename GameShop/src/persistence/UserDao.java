package persistence;

import domain.User;

import java.sql.ResultSet;
import java.util.List;

public interface UserDao {
    public List<User> findAllUsers();

    public User findUserByUsernameAndPassword(String username, String password);

    public User findUserById(int id);

    public boolean insertUser(User user);

    private User resultSetToUser(ResultSet resultSet) throws Exception {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}
