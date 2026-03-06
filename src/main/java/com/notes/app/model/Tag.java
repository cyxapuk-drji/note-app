package com.notes.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Tag")
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String color;
}
