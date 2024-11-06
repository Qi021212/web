package service;

import domain.User;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    public User getUserByUserId(int userId) {
        User user = null;
        String query = "SELECT id, username, password, email FROM userinfo WHERE id = ?";

        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(pstmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace(); // 可以进行更好的错误处理
        }

        return user;
    }
}
