package whj.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whj.bookstore.dao.CategoryMapper;
import whj.bookstore.model.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategories() {
        return categoryMapper.selectAllCategories();
    }

    public Category getCategoryById(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public Map<Integer, String> listAllCategoriesByMap() {
        List<Category> categories = categoryMapper.selectAllCategories();
        Map<Integer, String> categoriesMap = new HashMap<>();
        for (Category category : categories) {
            categoriesMap.put(category.getId(),category.getName());
        }
        return categoriesMap;
    }

}
