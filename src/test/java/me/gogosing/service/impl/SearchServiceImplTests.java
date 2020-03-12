package me.gogosing.service.impl;

import me.gogosing.model.Album;
import me.gogosing.model.Song;
import me.gogosing.persistence.entity.AlbumEntity;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.AlbumRepository;
import me.gogosing.persistence.repository.SongRepository;
import me.gogosing.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by JinBum Jeong on 2020/02/08.
 */
@ExtendWith(SpringExtension.class)
public class SearchServiceImplTests {

    @MockBean
    private AlbumRepository albumRepository;

    @MockBean
    private SongRepository songRepository;

    private SearchService searchService;

    @BeforeEach
    public void init() {
        searchService = new SearchServiceImpl(
                albumRepository, songRepository
        );
    }

    @Test
    public void testGetAlbumList() {
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
        albumEntity1.setId("FA200205999992");
        albumEntity1.setTitle("album title 2");
        albumEntity1.setDeleted(false);
        albumEntity1.setCreateOn(ZonedDateTime.now());

        SongEntity songEntity2 = new SongEntity();
        songEntity2.setId("FS200205999992");
        songEntity2.setAlbumEntity(albumEntity2);
        songEntity2.setTitle("song title 2");
        songEntity2.setLength(420);
        songEntity2.setTrackNo(1);
        songEntity2.setDeleted(false);
        songEntity2.setCreateOn(ZonedDateTime.now());

        albumEntity1.setSongEntities(Set.of(songEntity1));
        albumEntity2.setSongEntities(Set.of(songEntity2));

        ArgumentCaptor<String> titleArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> localeArgumentCaptor = ArgumentCaptor.forClass(String.class);

        List<AlbumEntity> expectedMockResult = Arrays.asList(albumEntity1, albumEntity2);

        when(
            albumRepository.getAlbumEntities(
                titleArgumentCaptor.capture(), localeArgumentCaptor.capture()
            )
        ).thenReturn(expectedMockResult);

        List<Album> actualResult = searchService
                .getAlbumList("album title", "ko");

        assertThat(actualResult)
                .as("앨범 목록 조회 테스트")
                .isNotNull()
                .isNotEmpty()
                .hasSize(expectedMockResult.size())
                .extracting("id")
                .containsExactly(albumEntity1.getId(), albumEntity2.getId());
    }

    @Test
    public void testGetSongList() {
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
        albumEntity1.setId("FA200205999992");
        albumEntity1.setTitle("album title 2");
        albumEntity1.setDeleted(false);
        albumEntity1.setCreateOn(ZonedDateTime.now());

        SongEntity songEntity2 = new SongEntity();
        songEntity2.setId("FS200205999992");
        songEntity2.setAlbumEntity(albumEntity2);
        songEntity2.setTitle("song title 2");
        songEntity2.setLength(420);
        songEntity2.setTrackNo(1);
        songEntity2.setDeleted(false);
        songEntity2.setCreateOn(ZonedDateTime.now());

        ArgumentCaptor<String> titleArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> localeArgumentCaptor = ArgumentCaptor.forClass(String.class);

        List<SongEntity> expectedMockResult = Arrays.asList(songEntity1, songEntity2);

        when(
            songRepository.getSongEntities(
                titleArgumentCaptor.capture(), localeArgumentCaptor.capture()
            )
        ).thenReturn(expectedMockResult);

        List<Song> actualResult = searchService
                .getSongList("album title", "ko");

        assertThat(actualResult)
                .as("음원 목록 조회 테스트")
                .isNotNull()
                .isNotEmpty()
                .hasSize(expectedMockResult.size())
                .extracting("id")
                .containsExactly(songEntity1.getId(), songEntity2.getId());
    }
}
