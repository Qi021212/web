package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private int age;
    private boolean isAdmin;

    public User() {

    }

    public User(String username, String password, String email, int age, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return id + "," + username + "," + password + "," + email + "," + age + "," + isAdmin;
    }
}
