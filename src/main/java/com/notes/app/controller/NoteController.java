package com.notes.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.notes.app.service.NoteService;

import jakarta.servlet.http.HttpSession;

import com.notes.app.model.Note;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/notes")
public class NoteController {
    
    @Autowired
    private NoteService noteService;

    // Главная старница со списком всех заметок
    @GetMapping
    public String showNotes(HttpSession session, Model model) {
        
        Long userId = (Long) session.getAttribute("username");
        if (userId == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("notes", noteService.getNotesByUseId(userId));

        return "notes";
    }

    // Получить форму редактирования заметок
    @GetMapping("/edit/{id}")
    public String getEditNotes(@PathVariable Long id, Model model) {
        
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);

        return "edit-note";
    }

    // Создание заметок
    @PostMapping
    public String createNotes(@RequestParam String title, @RequestParam String content,HttpSession session,Model model) {
    
        Long userId = (Long) session.getAttribute("userId");
    
        noteService.createNote(title, content, userId);  // новый метод
    
        model.addAttribute("notes", noteService.getNotesByUseId(userId));
        return "notes";
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
