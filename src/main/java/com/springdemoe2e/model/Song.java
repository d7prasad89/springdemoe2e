package com.springdemoe2e.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    private String album;
    private String artist;

    @Column(name = "is_favorite")
    private Boolean isFavorite;
    private String title;
}
