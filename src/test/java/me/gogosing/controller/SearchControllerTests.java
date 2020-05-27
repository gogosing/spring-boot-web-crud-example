package me.gogosing.controller;

import me.gogosing.model.Album;
import me.gogosing.model.Song;
import me.gogosing.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by JinBum Jeong on 2020/02/09.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SearchController.class)
public class SearchControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @Test
    public void testGetSearchResult() throws Exception {
        String title = "title";
        String locale = Locale.KOREAN.getLanguage();

        Song song1 = new Song();
        song1.setId("FS200205000001");
        song1.setTitle("song title 1");
        song1.setTrack(1);
        song1.setLength(1);

        Song song2 = new Song();
        song2.setId("FS200205000002");
        song2.setTitle("song title 2");
        song2.setTrack(2);
        song2.setLength(2);

        Album album1 = new Album();
        album1.setId("FA200205000001");
        album1.setTitle("album title 1");
        album1.setSongs(Arrays.asList(song1, song2));

        Song song3 = new Song();
        song3.setId("FS200205000003");
        song3.setTitle("song title 3");
        song3.setTrack(1);
        song3.setLength(3);

        Song song4 = new Song();
        song4.setId("FS200205000004");
        song4.setTitle("song title 4");
        song4.setTrack(2);
        song4.setLength(4);

        List<Album> albumList = Collections.singletonList(album1);
        List<Song> songList = Arrays.asList(song3, song4);

        Mockito
            .when(searchService.getAlbumList(title, locale))
            .thenReturn(albumList);

        Mockito
            .when(searchService.getSongList(title, locale))
            .thenReturn(songList);

        mockMvc.perform(
                get("/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("title", title)
                    .param("locale", locale)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.albums", hasSize(1)))
        .andExpect(jsonPath("$.albums[0].songs", hasSize(2)))
        .andExpect(jsonPath("$.songs", hasSize(2)));
    }
}
