package com.springdemoe2e.service;

import com.springdemoe2e.model.Song;
import com.springdemoe2e.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongService songService;

    private Song song1;
    private Song song2;

    @BeforeEach
    void setUp() {
        song1 = createSong(1L, "Song 1", "Artist 1");
        song2 = createSong(2L, "Song 2", "Artist 2");
    }

    @Test
    @DisplayName("getAllSongs should return all songs from repository")
    void getAllSongs_returnsSongsFromRepository() {
        List<Song> expected = List.of(song1, song2);

        given(songRepository.findAll()).willReturn(expected);

        List<Song> actual = songService.getAllSongs();

        assertNotNull(actual, "Returned list should not be null");
        assertEquals(expected.size(), actual.size(), "Returned list size should match");
        assertIterableEquals(expected, actual, "Returned songs should match expected");

        verify(songRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllSongs should return empty list when repository has no songs")
    void getAllSongs_returnsEmptyListWhenNoSongs() {
        given(songRepository.findAll()).willReturn(List.of());

        List<Song> actual = songService.getAllSongs();

        assertNotNull(actual);
        assertTrue(actual.isEmpty());

        verify(songRepository, times(1)).findAll();
    }

    private Song createSong(Long id, String title, String artist) {
        Song s = new Song();
        s.setId(id);
        s.setTitle(title);
        s.setArtist(artist);
        return s;
    }
}

