package com.notes.app.dto.response;

import lombok.Data;

@Data
public class TagResponse {
    
    private Long id;
    private String name;
    private String color;
    private Long noteCount;
}
