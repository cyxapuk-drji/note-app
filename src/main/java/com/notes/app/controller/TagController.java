package com.notes.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.notes.app.dto.response.TagResponse;
import com.notes.app.model.Tag;
import com.notes.app.service.TagService;

import jakarta.validation.Valid;

import com.notes.app.dto.request.TagRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;
    
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagByUserId(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        return tagService.getTagByIdAndUserId(id, userId).map(tag -> ResponseEntity.ok(convertToResponse(tag))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTagByUserId(@RequestHeader("User-Id") Long userId) {
        return ResponseEntity.ok(tagService.getAllTagByUserId(userId).stream().map(this::convertToResponse).toList());
    }

    @PostMapping()
    public ResponseEntity<TagResponse> createTag(@Valid @RequestBody TagRequest request, @RequestHeader("User-Id") Long userId) {
        Tag tag = tagService.createTagByUserId(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(tag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTagById(@PathVariable Long id, @Valid @RequestBody TagRequest request, @RequestHeader("User-Id") Long userId) {

        Tag updateTag = tagService.updateTagByIdAndUserId(id, request.getColor(), request.getTagName(), userId);
        
        if (updateTag != null) {
            return ResponseEntity.ok(convertToResponse(updateTag));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTagById(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        tagService.deleteTagByIdAndUserId(id, userId);
        return ResponseEntity.noContent().build();
    }

    private TagResponse convertToResponse(Tag tag) {
        
        TagResponse response = new TagResponse();
        response.setId(tag.getId());
        response.setColor(tag.getColor());
        response.setTagName(tag.getName());

        return response;
    }
}
