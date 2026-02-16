package com.notes.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.app.repository.NoteRepository;
import com.notes.app.model.Note;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // Получение списка со всеми заметками
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Сохрание  заметки
    public Note saveNote(String title, String content) {

        Note note = new Note();

        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());

        return noteRepository.save(note);
    }
    
    // Удаление заметки
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    // Обновление заметки
    public Note updateNote(Long id, String title, String content) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));

        note.setTitle(title);
        note.setContent(content);
        note.setUpdatedAt(LocalDateTime.now());

        return noteRepository.save(note);
    }
    
    // Получение заметки по id
    public Note getNoteById(Long id) {
        
        return noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note not found"));
    }
}
