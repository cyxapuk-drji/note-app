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

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note saveNote(String title, String content) {

        Note note = new Note();

        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());

        return noteRepository.save(note);
    }
    
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
