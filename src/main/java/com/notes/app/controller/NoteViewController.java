package com.notes.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.notes.app.service.NoteService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/notes")
public class NoteViewController {
    
    @Autowired
    private NoteService noteService;

    @GetMapping
    public String showNotes(Model model) {
        
        model.addAttribute("notes", noteService.getAllNotes());

        return "notes";
    }

    @PostMapping
    public String createNotes(@RequestParam String title, @RequestParam String content) {
        
        noteService.saveNote(title, content);

        return "redirect:/notes";
    }

    @PostMapping("/delete/{id}")
    public String deleteNotes(@PathVariable Long id) {
        noteService.deleteNote(id);
        return "redirect:/notes";
    }
}
