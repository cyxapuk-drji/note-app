package com.notes.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String name;

    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private int noteCount;
}
