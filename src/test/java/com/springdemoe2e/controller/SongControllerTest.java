package com.springdemoe2e.controller;

import com.springdemoe2e.model.Song;
import com.springdemoe2e.service.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SongController.class)
class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SongService songService;

    @Test
    void testGetAllSongs() throws Exception {
        // Mock the service to return a list of songs
        Song song1 = new Song();
        song1.setId(Long.valueOf(1L));
        song1.setTitle("Song 1");
        song1.setArtist("Artist 1");

        Song song2 = new Song();
        song2.setId(Long.valueOf(2L));
        song2.setTitle("Song 2");
        song2.setArtist("Artist 2");

        List<Song> songs = Arrays.asList(song1, song2);

        when(songService.getAllSongs()).thenReturn(songs);

        // Perform GET request and verify
        mockMvc.perform(get("/api/songs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Song 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Song 2")));

        verify(songService, times(1)).getAllSongs();
    }
}