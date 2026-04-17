package com.items.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.items.app.dto.request.ItemRequest;
import com.items.app.dto.response.ItemResponse;
import com.items.app.model.Item;
import com.items.app.model.Item.ItemType;
import com.items.app.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id).map(item -> ResponseEntity.ok(convertToResponse(item))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems(@RequestParam(required = false) String tag, @RequestParam(required = false) ItemType type, @RequestParam(required = false) String q) {    
        return ResponseEntity.ok(itemService.search(q, tag, type).stream().map(this::convertToResponse).toList());
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createNote(@Valid @RequestBody ItemRequest request) {
        Item item = itemService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateNoteById(@PathVariable Long id, @Valid @RequestBody ItemRequest request) {
        Item updateNote = itemService.updateItem(id, request);
        
        if (updateNote != null) {
            return ResponseEntity.ok(convertToResponse(updateNote));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/favorite")
    public ResponseEntity<ItemResponse> toogleFavorite(@PathVariable Long id) {
        Item item = itemService.toggleFavorite(id);
        if(item != null) {
            return ResponseEntity.ok(convertToResponse(item));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<ItemResponse>> getFavoriteNotes() {
        List<Item> notes = itemService.getFavoriteItems();
        List<ItemResponse> response = notes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private ItemResponse convertToResponse(Item item) {
        
           ItemResponse response = new ItemResponse();
           
            response.setId(item.getId());
            response.setTitle(item.getTitle());
            response.setContent(item.getContent());
            response.setCreatedAt(item.getCreatedAt());
            response.setUpdatedAt(item.getUpdatedAt());
            response.setIsFavorite(item.getIsFavorite());
            response.setTagName(item.getTagName() != null ? item.getTagName() : null);
            response.setTagColor(item.getTagColor());
            response.setType(item.getType());
            response.setPriority(item.getPriority());

            return response;
    }

}
