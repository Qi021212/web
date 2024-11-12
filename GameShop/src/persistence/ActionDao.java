package persistence;

import domain.Action;

import java.util.List;

public interface ActionDao {
    List<Action> getActionListByUserId(int userId);
    void insertAction(Action action);

}
