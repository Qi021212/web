package service;

import dao.ExistingGameDAO;
import model.ExistingGame;

import java.util.List;

public class ExistingGameService {
    private ExistingGameDAO existingGameDAO = new ExistingGameDAO();

    public boolean isGameAlreadyExist(int userId, int itemId) {
        return existingGameDAO.checkGameExists(userId, itemId);
    }

    public void addExistingGame(int userId, int itemId, String itemName) {
        existingGameDAO.insertExistingGame(userId, itemId, itemName);
    }

    public List<ExistingGame> getExistingGames(int userId) {
        return existingGameDAO.getExistingGamesByUserId(userId);
    }
}
