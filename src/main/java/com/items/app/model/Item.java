package com.items.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "item")
@DynamicUpdate
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private ItemType type;

    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String tagName;

    private String tagColor;

    @Column(name = "is_favorite", nullable = false)
    private Boolean isFavorite = false;

    public enum ItemType {
        NOTE, TASK
    }

    public enum Priority {
        LOW, MEDIUM, HIGH
    }
}
