package me.gogosing.controller;

import me.gogosing.model.Album;
import me.gogosing.model.Pages;
import me.gogosing.model.Pagination;
import me.gogosing.model.Song;
import me.gogosing.service.AlbumService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by JinBum Jeong on 2020/02/09.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AlbumController.class)
public class AlbumControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumService albumService;

    @Test
    public void testGetAlbumPagination() throws Exception {
        Pages pages = new Pages();
        pages.setFirst(null);
        pages.setPrev(null);
        pages.setLast("http://SERVER_URL/api/albums?page=2");
        pages.setNext("http://SERVER_URL/api/albums?page=2");

        Song song1 = new Song();
        song1.setId("FS200205000001");
        song1.setTitle("song title 1");
        song1.setTrack(1);
        song1.setLength(11);

        Song song2 = new Song();
        song2.setId("FS200205000002");
        song2.setTitle("song title 2");
        song2.setTrack(2);
        song2.setLength(12);

        Album album = new Album();
        album.setId("FA200205000001");
        album.setTitle("album title");
        album.setSongs(Arrays.asList(song1, song2));

        Pagination pagination = new Pagination();
        pagination.setPages(pages);
        pagination.setAlbums(Collections.singletonList(album));

        when(albumService.getAlbumPagination(1, 10, "ko"))
            .thenReturn(pagination);

        mockMvc.perform(
                get("/albums")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("page", String.valueOf(1))
                    .param("locale", "ko")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("$.pages.first").doesNotExist())
        .andExpect(jsonPath("$.pages.prev").doesNotExist())
        .andExpect(jsonPath("$.pages.last", equalTo(pages.getLast())))
        .andExpect(jsonPath("$.pages.next", equalTo(pages.getNext())))
        .andExpect(jsonPath("$.albums", hasSize(1)))
        .andExpect(jsonPath("$.albums[0].songs", hasSize(2)));
    }
}
