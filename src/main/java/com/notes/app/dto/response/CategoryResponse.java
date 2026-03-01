package com.notes.app.dto.response;

import lombok.Data;

@Data
public class CategoryResponse {
    
    private Long Id;
    private String name;
    private String color;
    private int noteCount;
}
