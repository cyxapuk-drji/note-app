package com.notes.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.app.repository.NoteRepository;
import com.notes.app.model.Category;
import com.notes.app.model.Note;

import java.util.List;
import java.time.LocalDateTime;
import com.notes.app.repository.UserRepository;
import com.notes.app.model.User;

@Service
public class NoteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    // Удаление заметки по id
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    // Обновление заметки по id
    public Note updateNote(Long noteId, String title, String content, Category category ) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));

        note.setTitle(title);
        note.setContent(content);
        note.setUpdatedAt(LocalDateTime.now());
        note.setCategory(category);

        return noteRepository.save(note);
    }
    
    // Получение заметки по id
    public Note getNoteById(Long id) {
        
        return noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    // получение  заметок пользователя по id
    public List<Note> getNotesByUserId(Long userId) {
        return noteRepository.findByUserId(userId);   
    }

    //создание заметки с id
    public Note createNote(String title, String content, Long userId, Category category) {
    
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);
        note.setCategory(category);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
    
        return noteRepository.save(note);
    }

    public List<Note> getNotesByCategoryAndUser(Long categoryId, Long userId) {
        return noteRepository.findByCategoryIdAndUserId(categoryId, userId);
    }

    public void toggleFavorite(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Заметка не найдена"));
        note.setFavorite(!note.isFavorite());
        noteRepository.save(note);
    }

    public List<Note> getNotesByUserIdSorted(Long userId) {
        return noteRepository.findByUserIdOrderByFavoriteDescCreatedAtDesc(userId);
    }

    public List<Note> getNotesByCategoryAndUserSorted(Long categoryId, Long userId) {
        return noteRepository.findByCategoryIdAndUserIdOrderByFavoriteDescCreatedAtDesc(categoryId, userId);
    }
}
