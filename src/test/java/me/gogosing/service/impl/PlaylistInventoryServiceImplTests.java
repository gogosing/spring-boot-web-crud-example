package me.gogosing.service.impl;

import me.gogosing.component.GenerateIdComponent;
import me.gogosing.model.Inventory;
import me.gogosing.model.InventoryRequest;
import me.gogosing.persistence.entity.AlbumEntity;
import me.gogosing.persistence.entity.PlaylistEntity;
import me.gogosing.persistence.entity.PlaylistInventoryEntity;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.AlbumRepository;
import me.gogosing.persistence.repository.PlaylistInventoryRepository;
import me.gogosing.persistence.repository.PlaylistRepository;
import me.gogosing.persistence.repository.SongRepository;
import me.gogosing.service.PlaylistInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by JinBum Jeong on 2020/02/08.
 */
@ExtendWith(SpringExtension.class)
public class PlaylistInventoryServiceImplTests {

    @MockBean
    private GenerateIdComponent generateIdComponent;

    @MockBean
    private SongRepository songRepository;

    @MockBean
    private AlbumRepository albumRepository;

    @MockBean
    private PlaylistRepository playlistRepository;

    @MockBean
    private PlaylistInventoryRepository playlistInventoryRepository;

    private PlaylistInventoryService playlistInventoryService;

    @BeforeEach
    public void init() {
        playlistInventoryService = new PlaylistInventoryServiceImpl(
                generateIdComponent, songRepository, albumRepository,
                playlistRepository, playlistInventoryRepository
        );
    }

    @Test
    public void testGetInventory() {
        String expectedUserId = "FU200205000001";
        String expectedPlaylistId = "FP20200208NZRH";

        AlbumEntity albumEntity1 = new AlbumEntity();
        albumEntity1.setId("FA200205999991");
        albumEntity1.setTitle("album title 1");
        albumEntity1.setDeleted(false);
        albumEntity1.setCreateOn(ZonedDateTime.now());

        SongEntity songEntity1 = new SongEntity();
        songEntity1.setId("FS200205999991");
        songEntity1.setAlbumEntity(albumEntity1);
        songEntity1.setTitle("song title 1");
        songEntity1.setLength(420);
        songEntity1.setTrackNo(1);
        songEntity1.setDeleted(false);
        songEntity1.setCreateOn(ZonedDateTime.now());

        AlbumEntity albumEntity2 = new AlbumEntity();
        albumEntity2.setId("FA200205999992");
        albumEntity2.setTitle("album title 2");
        albumEntity2.setDeleted(false);
        albumEntity2.setCreateOn(ZonedDateTime.now().plusDays(1));

        SongEntity songEntity2 = new SongEntity();
        songEntity2.setId("FS200205999992");
        songEntity2.setAlbumEntity(albumEntity2);
        songEntity2.setTitle("song title 2");
        songEntity2.setLength(420);
        songEntity2.setTrackNo(1);
        songEntity2.setDeleted(false);
        songEntity2.setCreateOn(ZonedDateTime.now());

        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(expectedPlaylistId);
        playlistEntity.setTitle("playlist title");
        playlistEntity.setCreateBy(expectedUserId);
        playlistEntity.setCreateOn(ZonedDateTime.now());

        PlaylistInventoryEntity playlistInventoryEntity1 = new PlaylistInventoryEntity();
        playlistInventoryEntity1.setId("FI20200208P0U3");
        playlistInventoryEntity1.setPlaylistEntity(playlistEntity);
        playlistInventoryEntity1.setSongEntity(songEntity1);
        playlistInventoryEntity1.setCreateOn(ZonedDateTime.now());

        PlaylistInventoryEntity playlistInventoryEntity2 = new PlaylistInventoryEntity();
        playlistInventoryEntity2.setId("FI20200208P0U4");
        playlistInventoryEntity2.setPlaylistEntity(playlistEntity);
        playlistInventoryEntity2.setSongEntity(songEntity2);
        playlistInventoryEntity2.setCreateOn(ZonedDateTime.now().plusDays(1));

        playlistEntity.setPlaylistInventoryEntities(
                Arrays.asList(playlistInventoryEntity1, playlistInventoryEntity2)
        );

        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> playlistIdArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(
            playlistRepository.getPlaylistEntityWithSongEntities(
                userIdArgumentCaptor.capture(), playlistIdArgumentCaptor.capture()
            )
        ).thenReturn(Optional.of(playlistEntity));

        List<Inventory> actualResult = playlistInventoryService
                .getInventory(expectedUserId, expectedPlaylistId);

        assertThat(actualResult)
                .as("플레이리스트 컨텐츠 목록 조회 검증")
                .isNotNull()
                .isNotEmpty()
                .hasSize(playlistEntity.getPlaylistInventoryEntities().size())
                .extracting("id")
                .containsExactly(playlistInventoryEntity1.getId(), playlistInventoryEntity2.getId());
    }

    @Test
    public void testAddInventory() {
        String expectedUserId = "FU200205000001";
        String expectedPlaylistInventoryId1 = "FI20200208NZR1";
        String expectedPlaylistInventoryId2 = "FI20200208NZR2";
        String expectedPlaylistInventoryId3 = "FI20200208NZR3";

        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId("FP20200208NZR1");
        playlistEntity.setTitle("playlist title");
        playlistEntity.setCreateBy(expectedUserId);
        playlistEntity.setCreateOn(ZonedDateTime.now());

        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> playlistIdArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(
            playlistRepository.getPlaylistEntity(
                userIdArgumentCaptor.capture(), playlistIdArgumentCaptor.capture()
            )
        ).thenReturn(Optional.of(playlistEntity));

        AlbumEntity albumEntity1 = new AlbumEntity();
        albumEntity1.setId("FA200205999993");
        albumEntity1.setTitle("album title 3");
        albumEntity1.setDeleted(false);
        albumEntity1.setCreateOn(ZonedDateTime.now());

        SongEntity songEntity1 = new SongEntity();
        songEntity1.setId("FS200205999991");
        songEntity1.setAlbumEntity(albumEntity1);
        songEntity1.setTitle("song title 1");
        songEntity1.setLength(420);
        songEntity1.setTrackNo(1);
        songEntity1.setDeleted(false);
        songEntity1.setCreateOn(ZonedDateTime.now());

        SongEntity songEntity2 = new SongEntity();
        songEntity2.setId("FS200205999992");
        songEntity2.setAlbumEntity(albumEntity1);
        songEntity2.setTitle("song title 2");
        songEntity2.setLength(420);
        songEntity2.setTrackNo(2);
        songEntity2.setDeleted(false);
        songEntity2.setCreateOn(ZonedDateTime.now());

        AlbumEntity albumEntity2 = new AlbumEntity();
        albumEntity2.setId("FA200205999992");
        albumEntity2.setTitle("album title 2");
        albumEntity2.setDeleted(false);
        albumEntity2.setCreateOn(ZonedDateTime.now());

        SongEntity songEntity3 = new SongEntity();
        songEntity3.setId("FS200205999993");
        songEntity3.setAlbumEntity(albumEntity2);
        songEntity3.setTitle("song title 3");
        songEntity3.setLength(420);
        songEntity3.setTrackNo(1);
        songEntity3.setDeleted(false);
        songEntity3.setCreateOn(ZonedDateTime.now());

        albumEntity1.setSongEntities(Arrays.asList(songEntity1, songEntity2));
        albumEntity2.setSongEntities(Collections.singletonList(songEntity3));

        ArgumentCaptor<String> localeArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<List<String>> songsArgumentCaptor = ArgumentCaptor.forClass(List.class);

        when(
            songRepository.getSongEntities(
                localeArgumentCaptor.capture(), songsArgumentCaptor.capture()
            )
        ).thenReturn(Collections.singletonList(songEntity3));

        ArgumentCaptor<List<String>> albumsArgumentCaptor = ArgumentCaptor.forClass(List.class);

        when(
            albumRepository.getAlbumEntities(
                localeArgumentCaptor.capture(), albumsArgumentCaptor.capture()
            )
        ).thenReturn(Collections.singletonList(albumEntity1));

        when(generateIdComponent.inventoryId())
            .thenReturn(expectedPlaylistInventoryId1, expectedPlaylistInventoryId2);

        ArgumentCaptor<List<PlaylistInventoryEntity>> inventoryArgumentCaptor =
                ArgumentCaptor.forClass(List.class);

        PlaylistInventoryEntity playlistInventoryEntity1 = new PlaylistInventoryEntity();
        playlistInventoryEntity1.setId(expectedPlaylistInventoryId1);
        playlistInventoryEntity1.setPlaylistEntity(playlistEntity);
        playlistInventoryEntity1.setSongEntity(songEntity1);
        playlistInventoryEntity1.setCreateOn(ZonedDateTime.now());

        PlaylistInventoryEntity playlistInventoryEntity2 = new PlaylistInventoryEntity();
        playlistInventoryEntity2.setId(expectedPlaylistInventoryId2);
        playlistInventoryEntity2.setPlaylistEntity(playlistEntity);
        playlistInventoryEntity2.setSongEntity(songEntity2);
        playlistInventoryEntity2.setCreateOn(ZonedDateTime.now());

        PlaylistInventoryEntity playlistInventoryEntity3 = new PlaylistInventoryEntity();
        playlistInventoryEntity3.setId(expectedPlaylistInventoryId3);
        playlistInventoryEntity3.setPlaylistEntity(playlistEntity);
        playlistInventoryEntity3.setSongEntity(songEntity3);
        playlistInventoryEntity3.setCreateOn(ZonedDateTime.now());

        when(
            playlistInventoryRepository.saveAll(inventoryArgumentCaptor.capture())
        ).thenReturn(
            Arrays.asList(playlistInventoryEntity1, playlistInventoryEntity2, playlistInventoryEntity3)
        );

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setAlbums(Collections.singletonList(albumEntity1.getId()));
        inventoryRequest.setSongs(Collections.singletonList(songEntity3.getId()));

        playlistInventoryService.addInventory(
            expectedUserId, playlistEntity.getId(), Locale.KOREAN.getLanguage(), inventoryRequest
        );

        verify(playlistRepository, times(1))
                .getPlaylistEntity(any(String.class), any(String.class));

        verify(songRepository, times(1))
                .getSongEntities(any(String.class), any(List.class));

        verify(albumRepository, times(1))
                .getAlbumEntities(any(String.class), any(List.class));

        verify(generateIdComponent, times(3))
                .inventoryId();

        verify(playlistInventoryRepository, times(1))
                .saveAll(any(List.class));
    }

    @Test
    public void testRemoveInventory() {
        String expectedUserId = "FU200205000001";
        String expectedPlaylistInventoryId1 = "FI20200208NZR1";
        String expectedPlaylistInventoryId2 = "FI20200208NZR2";
        String expectedPlaylistInventoryId3 = "FI20200208NZR3";

        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId("FP20200208NZR1");
        playlistEntity.setTitle("playlist title");
        playlistEntity.setCreateBy(expectedUserId);
        playlistEntity.setCreateOn(ZonedDateTime.now());

        AlbumEntity albumEntity1 = new AlbumEntity();
        albumEntity1.setId("FA200205999993");
        albumEntity1.setTitle("album title 3");
        albumEntity1.setDeleted(false);
        albumEntity1.setCreateOn(ZonedDateTime.now());

        SongEntity songEntity1 = new SongEntity();
        songEntity1.setId("FS200205999991");
        songEntity1.setAlbumEntity(albumEntity1);
        songEntity1.setTitle("song title 1");
        songEntity1.setLength(420);
        songEntity1.setTrackNo(1);
        songEntity1.setDeleted(false);
        songEntity1.setCreateOn(ZonedDateTime.now());

        SongEntity songEntity2 = new SongEntity();
        songEntity2.setId("FS200205999992");
        songEntity2.setAlbumEntity(albumEntity1);
        songEntity2.setTitle("song title 2");
        songEntity2.setLength(420);
        songEntity2.setTrackNo(2);
        songEntity2.setDeleted(false);
        songEntity2.setCreateOn(ZonedDateTime.now());

        AlbumEntity albumEntity2 = new AlbumEntity();
        albumEntity2.setId("FA200205999992");
        albumEntity2.setTitle("album title 2");
        albumEntity2.setDeleted(false);
        albumEntity2.setCreateOn(ZonedDateTime.now());

        SongEntity songEntity3 = new SongEntity();
        songEntity3.setId("FS200205999993");
        songEntity3.setAlbumEntity(albumEntity2);
        songEntity3.setTitle("song title 3");
        songEntity3.setLength(420);
        songEntity3.setTrackNo(1);
        songEntity3.setDeleted(false);
        songEntity3.setCreateOn(ZonedDateTime.now());

        albumEntity1.setSongEntities(Arrays.asList(songEntity1, songEntity2));
        albumEntity2.setSongEntities(Collections.singletonList(songEntity3));

        PlaylistInventoryEntity playlistInventoryEntity1 = new PlaylistInventoryEntity();
        playlistInventoryEntity1.setId(expectedPlaylistInventoryId1);
        playlistInventoryEntity1.setPlaylistEntity(playlistEntity);
        playlistInventoryEntity1.setSongEntity(songEntity1);
        playlistInventoryEntity1.setCreateOn(ZonedDateTime.now());

        PlaylistInventoryEntity playlistInventoryEntity2 = new PlaylistInventoryEntity();
        playlistInventoryEntity2.setId(expectedPlaylistInventoryId2);
        playlistInventoryEntity2.setPlaylistEntity(playlistEntity);
        playlistInventoryEntity2.setSongEntity(songEntity2);
        playlistInventoryEntity2.setCreateOn(ZonedDateTime.now());

        PlaylistInventoryEntity playlistInventoryEntity3 = new PlaylistInventoryEntity();
        playlistInventoryEntity3.setId(expectedPlaylistInventoryId3);
        playlistInventoryEntity3.setPlaylistEntity(playlistEntity);
        playlistInventoryEntity3.setSongEntity(songEntity3);
        playlistInventoryEntity3.setCreateOn(ZonedDateTime.now());

        List<PlaylistInventoryEntity> inventoryEntities = Arrays.asList(
                playlistInventoryEntity1, playlistInventoryEntity2, playlistInventoryEntity3
        );

        playlistEntity.setPlaylistInventoryEntities(inventoryEntities);

        ArgumentCaptor<String> userIdSpecArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> playlistIdSpecArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<List<String>> inventoryIdSpecArgumentCaptor = ArgumentCaptor.forClass(List.class);

        when(
            playlistInventoryRepository.getPlaylistInventoryEntities(
                userIdSpecArgumentCaptor.capture(),
                playlistIdSpecArgumentCaptor.capture(),
                inventoryIdSpecArgumentCaptor.capture()
            )
        ).thenReturn(inventoryEntities);

        doNothing()
            .when(playlistInventoryRepository).deleteAll(inventoryEntities);

        playlistInventoryService.removeInventory(
                expectedUserId, playlistEntity.getId(),
                inventoryEntities.stream()
                        .map(PlaylistInventoryEntity::getId)
                        .collect(Collectors.toList())
        );

        verify(playlistInventoryRepository, times(1))
                .deleteAll(any(List.class));
    }
}
