package com.notes.app.dto.request;

import lombok.Data;

@Data
public class CreateNoteRequest {
    
    private String title;
    private String content;
    private Long categoryId;
}
