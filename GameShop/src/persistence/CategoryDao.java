package persistence;

import domain.Category;

import java.util.List;

public interface CategoryDao {
    //获取category列表
    List<Category> getCategoryList();
    //通过categoryId获取category对象
    Category getCategoryById(String categoryId);
}
