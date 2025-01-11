package service;

import domain.Item;
import persistence.ItemDao;
import persistence.impl.ItemDaoImpl;

import java.util.List;

public class CatalogService {
    private ItemDao itemDao;
    public CatalogService() {
        this.itemDao = new ItemDaoImpl();
    }

    public List<Item> getItemBySrc(String src) {
        return itemDao.getItemBySrc(src);
    }
    public List<Item> getAllItem(){
        return itemDao.getAllItem();
    }
    public List<Item> getItemByCategory(String categoryId){
        return itemDao.getItemByCategory(categoryId);
    }
    public List<Item> searchItemList(String keyword){
        return itemDao.searchItemList("%"+keyword+"%");
    }
}
