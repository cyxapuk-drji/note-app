package com.notes.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagRequest {
    
    @NotBlank(message = "Поле не может быть пустым")
    private String tagName;

    private String color;
}
