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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NoteService {

    private static final Logger log = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;


    // Удаление заметки по id
    public void deleteNote(Long id) {
        log.warn("Удаление заметки с ID: {}", id);
        noteRepository.deleteById(id);
        log.info("Заметка {} удалена", id);
    }

    // Обновление заметки по id
    public Note updateNote(Long noteId, String title, String content, Category category ) {
        log.info("Обновление заметки ID: {}", noteId);
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
    
        log.info("Создание заметки для пользователя ID: {}", userId);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);
        note.setCategory(category);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
    
        log.info("Заметка создана с ID: {}", note.getId());
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
