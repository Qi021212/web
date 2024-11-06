package persistence;

import domain.Item;

import java.util.List;

public interface ItemDao {
    List<Item> getItemByCategory(String categoryId);
    List<Item> getAllItem();
    List<Item> searchItemList(String keyword);
    List<Item> getItemBySrc(String src);
    Item getItem(int itemId);
    List<Item> getAllItems();
    void insertItem(Item item);
    void deleteItem(int itemId);
    Item getItemById(int id);


}
