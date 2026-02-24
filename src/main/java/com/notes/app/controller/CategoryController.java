package com.notes.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.notes.app.service.CategoryService;
import com.notes.app.service.UserService;
import com.notes.app.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createCategory(@RequestParam String name, @RequestParam String color, HttpSession session) {
        
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/auth/login";
        }

        User user = userService.findById(userId);

        categoryService.createCategory(name, color, user);

        return "redirect:/notes";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/auth/login";
        }

        categoryService.deleteCategory(id);

        return "redirect:/notes";
    }
}
