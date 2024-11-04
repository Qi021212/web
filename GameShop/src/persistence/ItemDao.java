package persistence;

import domain.Item;

import java.util.List;

public interface ItemDao {
    List<Item> getItemByCategory(String categoryId);
    List<Item> getAllItem();
    List<Item> searchItemList(String keyword);
    Item getItem(int itemId);


}
