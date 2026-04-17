package com.items.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.items.app.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

    Optional<Tag> findByName(String name);

}