package dao;

import model.ExistingGame;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExistingGameDAO {
    public boolean checkGameExists(int userId, int itemId) {
        String query = "SELECT COUNT(*) FROM existing_games WHERE user_id = ? AND item_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertExistingGame(int userId, int itemId, String itemName) {
        String query = "INSERT INTO existing_games (user_id, item_id, item_name) VALUES (?, ?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, itemId);
            stmt.setString(3, itemName);
            stmt.executeUpdate();
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ExistingGame> getExistingGamesByUserId(int userId) {
        List<ExistingGame> games = new ArrayList<>();
        String query = "SELECT * FROM existing_games WHERE user_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ExistingGame game = new ExistingGame(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("item_id"),
                        rs.getString("item_name")
                );
                games.add(game);
            }
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }
}
