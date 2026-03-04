package com.notes.app.service;

import org.springframework.stereotype.Service;

import com.notes.app.repository.NoteRepository;
import com.notes.app.repository.TagRepository;

import lombok.RequiredArgsConstructor;

import com.notes.app.model.Tag;
import com.notes.app.model.Note;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    private final TagRepository tagRepository;

    // Удаление заметки по id
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    //создание заметки с id
    public Note createNote(String title, String content, String tag) {

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        
        if (tag != null && !tag.isEmpty()) {
            note.setTag(null);
        }
        note.setTag(tagRepository.findByName(tag).get());
    
        return noteRepository.save(note);
    }

    // Обновление заметки по id
    public Note updateNote(Long noteId, String title, String content, Tag tag ) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));

        note.setTitle(title);
        note.setContent(content);
        note.setUpdatedAt(LocalDateTime.now());
        note.setTag(tag);

        return noteRepository.save(note);
    }
    
    // Получение заметки по id
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }
}
