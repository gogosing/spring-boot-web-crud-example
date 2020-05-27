package me.gogosing.controller;

import me.gogosing.model.Inventory;
import me.gogosing.model.InventoryRequest;
import me.gogosing.model.Playlist;
import me.gogosing.model.PlaylistRequest;
import me.gogosing.model.Song;
import me.gogosing.service.PlaylistInventoryService;
import me.gogosing.service.PlaylistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by JinBum Jeong on 2020/02/09.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlaylistController.class)
public class PlaylistControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlaylistService playlistService;

    @MockBean
    private PlaylistInventoryService playlistInventoryService;

    @Test
    public void testPostPlaylist() throws Exception {
        String userId = "FU200205000001";
        String expectedPlaylistId = "FP20200208NZR1";

        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setTitle("playlist title");

        when(playlistService.createPlaylist(userId, playlistRequest.getTitle()))
            .thenReturn(expectedPlaylistId);

        mockMvc.perform(
                post("/playlist/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(playlistRequest))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo(expectedPlaylistId)));
    }

    @Test
    public void testAddInventory() throws Exception {
        String userId = "FU200205000001";
        String playlistId = "FP20200208NZR1";
        String locale = Locale.KOREAN.getLanguage();

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setAlbums(Arrays.asList("FA200205000001", "FA200205000002"));
        inventoryRequest.setSongs(Arrays.asList("FS200205000001", "FS200205000002"));

        doNothing()
            .when(playlistInventoryService)
                .addInventory(userId, playlistId, locale, inventoryRequest);

        mockMvc.perform(
                post("/playlist/{userId}/{id}/inventory", userId, playlistId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-Locale-With", locale)
                    .content(objectMapper.writeValueAsString(inventoryRequest))
        )
        .andDo(print())
        .andExpect(status().isOk());

        verify(playlistInventoryService, times(1))
                .addInventory(any(String.class), any(String.class),  any(String.class), any(InventoryRequest.class));
    }

    @Test
    public void testGetPlaylist() throws Exception {
        String userId = "FU200205000001";

        Playlist playlist1 = new Playlist();
        playlist1.setId("FP20200208NZR1");
        playlist1.setTitle("playlist title 1");
        playlist1.setCreateOn(ZonedDateTime.now());

        Playlist playlist2 = new Playlist();
        playlist2.setId("FP20200208NZR2");
        playlist2.setTitle("playlist title 2");
        playlist2.setCreateOn(ZonedDateTime.now());

        when(playlistService.getPlaylist(userId))
            .thenReturn(Arrays.asList(playlist1, playlist2));

        mockMvc.perform(
                get("/playlist/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", equalTo(playlist1.getId())))
        .andExpect(jsonPath("$[1].id", equalTo(playlist2.getId())));
    }

    @Test
    public void testDeletePlaylist() throws Exception {
        String userId = "FU200205000001";
        String playlistId = "FP20200208NZR1";

        doNothing()
            .when(playlistService).deletePlaylist(userId, playlistId);

        mockMvc.perform(
                delete("/playlist/{userId}/{id}", userId, playlistId)
                    .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk());

        verify(playlistService, times(1))
                .deletePlaylist(any(String.class), any(String.class));
    }

    @Test
    public void testPutPlaylist() throws Exception {
        String userId = "FU200205000001";
        String playlistId = "FP20200208NZR1";

        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setTitle("playlist title modified");

        doNothing()
            .when(playlistService).updatePlaylist(userId, playlistId, playlistRequest.getTitle());

        mockMvc.perform(
                put("/playlist/{userId}/{id}", userId, playlistId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(playlistRequest))
        )
        .andDo(print())
        .andExpect(status().isOk());

        verify(playlistService, times(1))
            .updatePlaylist(any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void testGetInventory() throws Exception {
        String userId = "FU200205000001";
        String playlistId = "FP20200208NZR1";

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

        Inventory inventory1 = new Inventory();
        inventory1.setId("FI20200208P0U1");
        inventory1.setSong(song1);
        inventory1.setCreateOn(ZonedDateTime.now());

        Inventory inventory2 = new Inventory();
        inventory2.setId("FI20200208P0U2");
        inventory2.setSong(song2);
        inventory2.setCreateOn(ZonedDateTime.now());

        when(playlistInventoryService.getInventory(userId, playlistId))
            .thenReturn(Arrays.asList(inventory1, inventory2));

        mockMvc.perform(
                get("/playlist/{userId}/{id}/inventory", userId, playlistId)
                    .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", equalTo(inventory1.getId())))
        .andExpect(jsonPath("$[1].id", equalTo(inventory2.getId())));
    }

    @Test
    public void testRemoveInventory() throws Exception {
        String userId = "FU200205000001";
        String playlistId = "FP20200208NZR1";

        List<String> inventoryIdList = Collections.singletonList("FI20200208P0U1, FI20200208P0U2");

        doNothing()
            .when(playlistInventoryService)
                .removeInventory(userId, playlistId, inventoryIdList);

        mockMvc.perform(
                delete("/playlist/{userId}/{id}/inventory", userId, playlistId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inventoryIdList))
        )
        .andDo(print())
        .andExpect(status().isOk());

        verify(playlistInventoryService, times(1))
            .removeInventory(any(String.class), any(String.class), any(List.class));
    }
}
