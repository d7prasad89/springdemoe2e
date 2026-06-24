package com.springdemoe2e.controller;

import com.springdemoe2e.exception.ResourceNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/songs")
@Profile("test")
public class SongController {

    /**
     * Get all songs
     */
    @GetMapping
    public ResponseEntity<Map<String, String>> getAllSongs() {
        System.out.println("Getting all songs from dev profile");
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "All songs retrieved successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Get song by ID
     * Example: localhost:8080/api/songs/1
     */
    @GetMapping("/{songId}")
    public ResponseEntity<Map<String, String>> getSongById(@PathVariable Long songId) {
        System.out.println("Getting song with ID: " + songId);
        
        // Simulate validation - throw exception if ID is invalid
        if (songId == null || songId <= 0) {
            throw new IllegalArgumentException("Song ID must be a positive number");
        }
        
        // Simulate checking if song exists in database
        // For demo purposes, we'll throw exception if ID > 100
        if (songId > 100) {
            throw new ResourceNotFoundException("Song with ID " + songId + " not found in database");
        }
        
        Map<String, String> response = new HashMap<>();
        response.put("id", songId.toString());
        response.put("name", "Song " + songId);
        response.put("artist", "Artist " + songId);
        return ResponseEntity.ok(response);
    }
}
