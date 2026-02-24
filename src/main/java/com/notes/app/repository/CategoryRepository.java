package com.notes.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.notes.app.model.Category;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    List<Category> findByUserId(Long userId);
}
