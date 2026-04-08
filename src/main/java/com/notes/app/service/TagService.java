package com.notes.app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.notes.app.dto.request.TagRequest;
import com.notes.app.model.Tag;
import com.notes.app.repository.TagRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    
    private final TagRepository tagRepository;

    private static final String DEFAULT_COLOR = "#FFFFFF";
    
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
    
    public Tag createTag(TagRequest request) {

        if (tagRepository.findByName(request.getTagName()).isPresent()) {
            throw new RuntimeException("Тег с таким именем уже существует");
        }
        
        Tag tag = new Tag();
        tag.setName(request.getTagName());

        if (request.getColor() != null){   
            tag.setColor(request.getColor());
        }else{
            tag.setColor(DEFAULT_COLOR);
        }
        
        return tagRepository.save(tag);
    }
    
    public Tag updateTagById(Long tagId, String color, String name) {
        
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        
        if (optionalTag.isPresent()) {
            Tag tag = optionalTag.get();
            if (name != null && !name.equals(tag.getName())) {
                if (tagRepository.findByName(name).isPresent()) {
                    throw new RuntimeException("Tag with name '" + name + "' already exists");
                }
                tag.setName(name);
            } 
        if (color != null) {
            tag.setColor(color);
        }
            return tagRepository.save(tag);
        }
        return null;
    }
    
    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }

    public Tag findTagByTagName(String tagName) {
        return tagRepository.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found"));
    }
}
