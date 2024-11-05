package utils;

import domain.Item;
import persistence.ItemDAO;

import java.util.List;

public class JDBCDemo {
    public static void main(String[] args) {

        //插入商品测试数据
        //insertSampleItems(); // 调用插入商品的方法
        displayItems(); // 调用显示商品的方法

//        ItemDAO itemDAO = new ItemDAO();
//        List<Item> itemList = ItemDAO.getAllItems();
//        System.out.println(itemList);
//        for(Item item : itemList) { //打印出换行结果
//            System.out.println(item);
//        }

//        User user = new User("yyy","111","yyy@csu.cn",20,false);
//        UserDao userDao = new UserDao();
//        boolean flag = userDao.insertUser(user);
//        if(flag) {
//            System.out.println("新增成功");
//        }else{
//            System.out.println("failure");
//        }

//        UserDao userDao = new UserDao();
////        User user = userDao.findUserByUsernameAndPassword("aaa","111");
////        System.out.println(user);
//        List<User> userList = userDao.findAllUsers();
////        System.out.println(userList);
//        for(User user : userList) { //打印出换行结果
//            System.out.println(user);
//        }

//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/gameshop?serverTimezone=Asia/Shanghai", "root", "123456");
//            String sql = "INSERT INTO userinfo (username,password,email,age,is_admin) " +
//                    " VALUES ('xxx','888888','xxx@csu...',22,1)";
//            Statement statement = connection.createStatement();
//            int rows = statement.executeUpdate(sql);
//            if(rows == 1){
//                System.out.println("新增用户信息成功");
//            }else{
//                System.out.println("新增用户失败");
//            }
//            statement.close();
//            connection.close();
//
////            Class.forName("com.mysql.cj.jdbc.Driver");
////            Connection connection = DriverManager.getConnection(
////                    "jdbc:mysql://localhost:3306/gameshop?serverTimezone=Asia/Shanghai", "root", "123456");
////            String sql = "SELECT * FROM userinfo WHERE id=1";
////            Statement statement = connection.createStatement();
////            ResultSet resultSet = statement.executeQuery(sql);
////            if(resultSet.next()){
////                int id = resultSet.getInt("id");
////                String username = resultSet.getString("username");
////                String password = resultSet.getString(3);
////                String email = resultSet.getString(4);
////                int age = resultSet.getInt("age");
////                boolean is_admin = resultSet.getInt(6) == 0;
////                System.out.println(id+","+username+","+password+","+email+","+age+","+is_admin);
////            }
//////            PreparedStatement preparedStatement = connection.prepareStatement(sql);
////            statement.close();
////            resultSet.close();
////            connection.close();
////            System.out.println(connection);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }

    private static void insertSampleItems() {
        ItemDAO itemDAO = new ItemDAO();

        // 示例商品
        Item item1 = new Item("商品1",99.99,"描述1");
        Item item2 = new Item("商品2",149.99,"描述2");
        Item item3 = new Item("商品3",199.99,"描述3");

        // 插入商品
        itemDAO.insertItem(item1);
        itemDAO.insertItem(item2);
        itemDAO.insertItem(item3);

        System.out.println("Sample items inserted successfully.");
    }

    private static void displayItems() {
        ItemDAO itemDAO = new ItemDAO();
        List<Item> items = itemDAO.getAllItems();
        for (Item item : items) {
            System.out.println(item.getName() + " - " + item.getPrice() + " - " + item.getDescription());
        }
    }
}
