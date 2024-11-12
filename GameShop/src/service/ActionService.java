package service;

import domain.Action;
import persistence.ActionDao;
import persistence.impl.ActionDaoImpl;

import java.util.List;

public class ActionService {
    private ActionDao actionDao;
    public ActionService() {
        this.actionDao = new ActionDaoImpl();
    }
    public List<Action> getActionListByUserId(int userId) {
        return actionDao.getActionListByUserId(userId);
    }
    public void insertAction(Action action) {
        actionDao.insertAction(action);
    }
}
