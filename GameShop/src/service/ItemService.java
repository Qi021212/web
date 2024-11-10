package service;

import domain.Item;
import persistence.ItemDao;
import persistence.impl.ItemDaoImpl;

public class ItemService {
    private ItemDao itemDAO = new ItemDaoImpl();

    public Item getItemById(int itemId) {
        return itemDAO.getItemById(itemId);
    }
}
