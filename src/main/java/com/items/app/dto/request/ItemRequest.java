package com.items.app.dto.request;

import lombok.Data;

import com.items.app.model.Item.ItemType;
import com.items.app.model.Item.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
