package service;

import domain.ExistingGame;
import persistence.ExistingGameDAO;

import java.util.List;

public class ExistingGameService {
    private ExistingGameDAO existingGameDAO = new ExistingGameDAO();

    // 检查游戏是否已经存在
    public boolean isGameAlreadyExist(int userId, int itemId) {
        return existingGameDAO.checkGameExists(userId, itemId);
    }

    // 将游戏添加到已购买列表
    public void addExistingGame(int userId, int itemId, String itemName) {
        existingGameDAO.insertExistingGame(userId, itemId, itemName);
    }

    public List<ExistingGame> getExistingGames(int userId) {
        return existingGameDAO.getExistingGamesByUserId(userId);
    }
}
