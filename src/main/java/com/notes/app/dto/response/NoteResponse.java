package com.notes.app.dto.response;

import java.time.LocalDateTime;

import com.notes.app.model.Note;

import lombok.Data;

@Data
public class NoteResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private boolean favorite;
    private Long categoryId;
    private String categoryName;
    private String categoryColor;
    
    public static NoteResponse fromEntity(Note note) {
        NoteResponse response = new NoteResponse();
        response.setId(note.getId());
        response.setTitle(note.getTitle());
        response.setContent(note.getContent());
        response.setCreatedAt(note.getCreatedAt());
        response.setUpdatedAt(note.getUpdatedAt());
        response.setFavorite(note.isFavorite());
        
        if (note.getCategory() != null) {
            response.setCategoryId(note.getCategory().getId());
            response.setCategoryName(note.getCategory().getName());
            response.setCategoryColor(note.getCategory().getColor());
        }
        
        return response;
    }
}
