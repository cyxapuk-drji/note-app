package com.notes.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.notes.app.model.Item;
import com.notes.app.model.Item.ItemType;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.type = :type ORDER BY i.updatedAt DESC")
    List<Item> findByType(@Param("type") ItemType type);
    
    @Query("SELECT i FROM Item i WHERE i.tagName = :tagName ORDER BY i.updatedAt DESC")
    List<Item> findByTagName(@Param("tagName") String tagName);
    
    @Query("SELECT i FROM Item i WHERE i.isFavorite = true ORDER BY i.updatedAt DESC")
    List<Item> findFavoriteItems();

    @Query("SELECT i FROM Item i WHERE LOWER(i.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " + 
           "LOWER(i.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Item> findByQuery(@Param("query") String query);
} 
    
