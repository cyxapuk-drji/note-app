package com.items.app.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import com.items.app.dto.request.ItemRequest;
import com.items.app.model.Item;
import com.items.app.model.Tag;
import com.items.app.model.Item.ItemType;
import com.items.app.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    
    private final ItemRepository itemRepository;
    
    private final TagService tagService;

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item createItem(ItemRequest request) {
        
        Item item = new Item();

        if (request.getTagName() != null && !request.getTagName().isBlank()) {
            Tag tag = tagService.findTagByTagName(request.getTagName());
            item.setTagName(tag.getName());
            item.setTagColor(tag.getColor());
        }

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
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        
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
        itemRepository.deleteById(id);
    }

    public List<Item> getFavoriteItems() {
        return itemRepository.findFavoriteItems();
    }

    public Item toggleFavorite(Long id) {
        Optional<Item> optionalNote = itemRepository.findById(id);
        if(optionalNote.isPresent()) {
            Item item = optionalNote.get();
            item.setIsFavorite(!item.getIsFavorite());
            return itemRepository.save(item);
        }
        return null;
    }

    public List<Item> search(String query, String tagName, ItemType type) {
        if (query != null && !query.isBlank()) {
            return itemRepository.findByQuery(query);
        }
        if (tagName != null && !tagName.isBlank()) {
        return itemRepository.findByTagName(tagName);
        }
        if (type != null) {
            return itemRepository.findByType(type);
        }
        return itemRepository.findAll();
    }
}
