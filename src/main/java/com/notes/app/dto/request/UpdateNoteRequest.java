package com.notes.app.dto.request;

import lombok.Data;

@Data
public class UpdateNoteRequest {
    
    private String title;
    private String content;
    private Long tagId;
}
