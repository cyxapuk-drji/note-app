package com.items.app.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import com.items.app.dto.request.ItemRequest;
import com.items.app.model.Item;
import com.items.app.model.Tag;
import com.items.app.model.User;
import com.items.app.model.Item.ItemType;
import com.items.app.repository.ItemRepository;
import com.items.app.repository.UserRepository;
import com.items.app.security.services.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final TagService tagService;

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findByIdAndUserId(id, getUserId());
    }

    public Item createItem(ItemRequest request) {

        Item item = new Item();
        User user = userRepository.findById(getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getTagName() != null && !request.getTagName().isBlank()) {
            Tag tag = tagService.findTagByTagName(request.getTagName());
            item.setTagName(tag.getName());
            item.setTagColor(tag.getColor());
        }

        item.setUser(user);
        item.setTitle(request.getTitle());
        item.setContent(request.getContent());
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        if (request.getType() != null) {
            item.setType(request.getType());
            if (request.getType() == ItemType.TASK) {
                item.setPriority(request.getPriority());
            }
        }

        return itemRepository.save(item);
    }

    public Item updateItem(Long itemId, ItemRequest request) {
        Item item = itemRepository.findByIdAndUserId(itemId, getUserId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (request.getTagName() != null && !request.getTagName().isBlank()) {
            try {
                Tag tag = tagService.findTagByTagName(request.getTagName());
                item.setTagName(tag.getName());
                item.setTagColor(tag.getColor());
            } catch (RuntimeException e) {
                item.setTagName(null);
                item.setTagColor(null);
            }
        } else {
            item.setTagName(null);
            item.setTagColor(null);
        }

        item.setTitle(request.getTitle());
        item.setContent(request.getContent());
        item.setUpdatedAt(LocalDateTime.now());

        if (request.getType() != null) {
            item.setType(request.getType());
            if (request.getType() == ItemType.TASK) {
                item.setPriority(request.getPriority());
            }
        }

        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteByIdAndUserId(id, getUserId());
    }

    public List<Item> getFavoriteItems() {
        return itemRepository.findFavoriteItemsAndUserId(getUserId());
    }

    public Item toggleFavorite(Long id) {
        Optional<Item> optionalNote = itemRepository.findByIdAndUserId(id, getUserId());
        if (optionalNote.isPresent()) {
            Item item = optionalNote.get();
            item.setIsFavorite(!item.getIsFavorite());
            return itemRepository.save(item);
        }
        return null;
    }

    public List<Item> search(String query, String tagName, ItemType type) {
        if (query != null && !query.isBlank()) {
            return itemRepository.findByQueryAndUserId(query, getUserId());
        }
        if (tagName != null && !tagName.isBlank()) {
            return itemRepository.findByTagNameAndUserId(tagName, getUserId());
        }
        if (type != null) {
            return itemRepository.findByTypeAndUserId(type, getUserId());
        }
        return itemRepository.findByUserIdOrderByUpdatedAtDesc(getUserId());
    }

    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }
}
