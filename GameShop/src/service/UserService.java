package service;

import persistence.UserDao;
import domain.User;
import persistence.impl.UserDaoImpl;

import java.util.List;

public class UserService {
    private UserDao userDao;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserService() {
        this.userDao = new UserDaoImpl();
    }
    public User login(String username, String password) {
        User loginUser = this.userDao.findUserByUsernameAndPassword(username, password);
        if (loginUser == null) {
            this.msg = "用户名或密码错误";
            return null;
        }
        return loginUser;
    }

    public List<User> getAllUsers() {
        return this.userDao.findAllUsers();
    }

    public boolean addUser(User registerUser) {
        String username =  registerUser.getUsername();
        if(userDao.findUserByName(username)){
            this.msg = "该用户名已存在";
            return false;
        }
        return this.userDao.insertUser(registerUser);
    }
    public User findUserById(int id){
        return this.userDao.findUserById(id);
    }
}
