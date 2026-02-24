package com.notes.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.notes.app.model.Category;
import com.notes.app.repository.CategoryRepository;
import java.util.List;
import com.notes.app.model.User;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategoryByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    public Category createCategory(String name, String color, User user) {

        Category category = new Category();

        category.setName(name);
        category.setColor(color);
        category.setUser(user);

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
