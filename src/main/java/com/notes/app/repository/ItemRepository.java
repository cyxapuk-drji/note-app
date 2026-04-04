package com.notes.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.notes.app.model.Item;
import com.notes.app.model.Item.ItemType;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT i FROM Item i WHERE i.isFavorite = true AND i.userId = :userId")
    List<Item> findFavoriteNotesByUser(Long userId);
    
    List<Item> findByUserId(Long userId);
    
    void deleteByIdAndUserId(Long id, Long userId);

    @Query("SELECT i FROM Item i WHERE i.type = :type AND i.userId = :userId")
    List<Item> findByTypeAndUserId(@Param("type") ItemType type, @Param("userId") Long userId);

    @Query("SELECT i FROM Item i WHERE i.tag.name = :tagName AND i.userId = :userId")
    List<Item> findByTagNameAndUserId(@Param("tagName") String tagName, @Param("userId") Long userId);
} 
    
