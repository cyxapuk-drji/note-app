package com.items.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.items.app.dto.request.TagRequest;
import com.items.app.model.Tag;
import com.items.app.model.User;
import com.items.app.repository.TagRepository;
import com.items.app.repository.UserRepository;
import com.items.app.security.services.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    private final UserRepository userRepository;

    private static final String DEFAULT_COLOR = "#FFFFFF";

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findByIdAndUserId(id, getUserId());
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAllByUserId(getUserId());
    }

    public Tag createTag(TagRequest request) {

        if (tagRepository.findByNameAndUserId(request.getTagName(), getUserId()).isPresent()) {
            throw new RuntimeException("Тег с таким именем уже существует");
        }

        Tag tag = new Tag();

        User user = userRepository.findById(getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        tag.setName(request.getTagName());
        tag.setUser(user);

        if (request.getColor() != null) {
            tag.setColor(request.getColor());
        } else {
            tag.setColor(DEFAULT_COLOR);
        }

        return tagRepository.save(tag);
    }

    public Tag updateTagById(Long tagId, String color, String name) {

        Optional<Tag> optionalTag = tagRepository.findById(tagId);

        if (optionalTag.isPresent()) {
            Tag tag = optionalTag.get();
            if (name != null && !name.equals(tag.getName())) {
                if (tagRepository.findByNameAndUserId(name, getUserId()).isPresent()) {
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
        tagRepository.deleteByIdAndUserId(id, getUserId());
    }

    public Tag findTagByTagName(String tagName) {
        return tagRepository.findByNameAndUserId(tagName, getUserId())
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }
}
