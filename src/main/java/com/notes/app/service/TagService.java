package com.notes.app.service;

import org.springframework.stereotype.Service;
import com.notes.app.model.Tag;
import com.notes.app.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    
    private final TagRepository tagRepository;

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public Tag createTag(String color, String name) {

        Tag tag = new Tag();
        tag.setColor(color);
        tag.setName(name);

        return tagRepository.save(tag);
    }

    public Tag updateTag(Long tagId, String color, String name) {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new RuntimeException("Tag not found"));

        tag.setColor(color);
        tag.setName(name);

        return tagRepository.save(tag);
    }
}
