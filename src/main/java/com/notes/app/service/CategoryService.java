package com.notes.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.notes.app.model.Category;
import com.notes.app.repository.CategoryRepository;
import com.notes.app.repository.NoteRepository;

import java.util.List;
import com.notes.app.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NoteRepository noteRepository;

    public List<Category> getCategoryByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    public Category createCategory(String name, String color, User user) {

        log.info("Создание категории '{}' для пользователя ID: {}", name, user.getId());

        Category category = new Category();

        category.setName(name);
        category.setColor(color);
        category.setUser(user);

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        log.warn("Удаление категории ID: {}", id);
        categoryRepository.deleteById(id);
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getCategoriesWithCount(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);
        for (Category cat : categories) {
            int count = noteRepository.countByCategoryIdAndUserId(cat.getId(), userId);
            cat.setNoteCount(count);
        }
        return categories;
    }
}
