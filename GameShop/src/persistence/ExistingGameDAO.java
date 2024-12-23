package persistence;

import domain.ExistingGame;

import java.util.List;

public interface ExistingGameDAO {
    public boolean checkGameExists(int userId, int itemId);
    public void insertExistingGame(int userId, int itemId, String itemName);
    public List<ExistingGame> getExistingGamesByUserId(int userId);
}
