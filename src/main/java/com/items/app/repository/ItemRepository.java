package com.items.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.items.app.model.Item;
import com.items.app.model.Item.ItemType;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByIdAndUserId(Long id, Long userId);

    List<Item> findByUserIdOrderByUpdatedAtDesc(Long userId);

    void deleteByIdAndUserId(Long id, Long userId);

    @Query("SELECT i FROM Item i WHERE i.type = :type AND i.user.id = :userId ORDER BY i.updatedAt DESC")
    List<Item> findByTypeAndUserId(@Param("type") ItemType type, @Param("userId") Long userId);

    @Query("SELECT i FROM Item i WHERE i.tagName = :tagName AND i.user.id = :userId ORDER BY i.updatedAt DESC")
    List<Item> findByTagNameAndUserId(@Param("tagName") String tagName, @Param("userId") Long userId);

    @Query("SELECT i FROM Item i WHERE i.isFavorite = true AND i.user.id = :userId ORDER BY i.updatedAt DESC")
    List<Item> findFavoriteItemsAndUserId(@Param("userId") Long userId);

    @Query("SELECT i FROM Item i WHERE i.user.id = :userId AND LOWER(i.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(i.content) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY i.updatedAt DESC")
    List<Item> findByQueryAndUserId(@Param("query") String query, @Param("userId") Long userId);
}
