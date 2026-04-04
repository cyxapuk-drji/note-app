package com.notes.app.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.notes.app.model.Item.ItemType;
import com.notes.app.model.Item.Priority;

@Data
public class ItemRequest {
    
    @NotBlank(message = "Поле не может быть пустым")
    private String title;

    @NotBlank(message = "Поле не может быть пустым")
    private String content;

    private String tagName;

    @NotNull
    private ItemType type;
    
    private Priority priority;
}
