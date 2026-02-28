package com.notes.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.notes.app.service.*;
import com.notes.app.model.Category;
import com.notes.app.model.User;

import jakarta.servlet.http.HttpSession;

import com.notes.app.model.Note;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {
    
    @Autowired
    private NoteService noteService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    // Главная старница со списком всех заметок
    @GetMapping
    public String showNotes(@RequestParam(required = false) Long categoryId, HttpSession session, Model model) {
        
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/auth/login";
        }

        List<Note> notes;

        if (categoryId != null) {
            notes = noteService.getNotesByCategoryAndUser(categoryId, userId);
            Category currentCat = categoryService.getCategory(userId);
            model.addAttribute("currentCategory", currentCat);
        } else {
            notes = noteService.getNotesByUserId(userId);
        }

        List<Category> categories = categoryService.getCategoryByUserId(userId);

        model.addAttribute("notes", notes);
        model.addAttribute("categories", categories);

        return "notes";
    }

    // Получить форму редактирования заметок
    @GetMapping("/edit/{id}")
    public String getEditNotes(@PathVariable Long id, HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/auth/login";
        
        Note note = noteService.getNoteById(id);
        List<Category> categories = categoryService.getCategoryByUserId(userId); 

        model.addAttribute("categories", categories);
        model.addAttribute("note", note);

        return "edit-note";
    }

    // Создание заметок
    @PostMapping
    public String createNotes(@RequestParam String title, @RequestParam String content,
                              @RequestParam(required = false) Long categoryId, HttpSession session, Model model) {
    
        Long userId = (Long) session.getAttribute("userId");

            if (userId == null) {
                return "redirect:/auth/login";
            }
    
            User user = userService.findById(userId);

            Category category = null;
            if (categoryId != null) {
                 category = categoryService.getCategory(categoryId);
            }
    
        noteService.createNote(title, content, user.getId(), category);
    
        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        return "redirect:/notes";
    }

    // Удаление заметки
    @PostMapping("/delete/{id}")
    public String deleteNotes(@PathVariable Long id) {
        
        noteService.deleteNote(id);
        
        return "redirect:/notes";
    }

    // Обновление заметки после редактирования
    @PostMapping("/update/{id}")
    public String updateNotes(@PathVariable Long id, @RequestParam String title, @RequestParam String content) {
        
        noteService.updateNote(id, title, content);
        
        return "redirect:/notes";
    }
}
