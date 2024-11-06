package persistence;

import domain.User;

import java.util.List;

public interface UserDao {
    public List<User> findAllUsers();

    public User findUserByUsernameAndPassword(String username, String password);

    public User findUserById(int id);

    public boolean insertUser(User user);

    public  boolean findUserByName(String username);
}
