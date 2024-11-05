package utils;

import javax.sql.DataSource;
import java.sql.*;

public class DBUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //?serverTimezone=Asia/Shanghai
    private static final String URL = "jdbc:mysql://localhost:3306/gameshop?serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

//    //获取连接
//    public static Connection getConnection() {
//        Connection connection = null;
//        try{
//            Class.forName(DRIVER);
//            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return connection;
//    }

    //获取连接
    public static Connection getConnection() {
        Connection connection = null;
//        try{
//            Class.forName(DRIVER);
//            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("数据库连接成功"); // 连接成功的提示
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动类未找到: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("数据库连接失败: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }



    //关闭连接
    public static void closeConnection(Connection connection) {
        if(connection != null){
            try{
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if(statement != null){
            try{
                statement.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        if(preparedStatement != null){
            try{
                preparedStatement.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if(resultSet != null){
            try{
                resultSet.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        System.out.println(DBUtil.getConnection());
    }
}


