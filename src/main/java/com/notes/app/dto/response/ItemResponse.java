package com.notes.app.dto.response;

import java.time.LocalDateTime;

import com.notes.app.model.Item.ItemType;
import com.notes.app.model.Item.Priority;

import lombok.Data;

@Data
public class ItemResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private String tagName;
    private Boolean isFavorite;
    private ItemType type;
    private Priority priority;
}
