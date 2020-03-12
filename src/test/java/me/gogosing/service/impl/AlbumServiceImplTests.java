package me.gogosing.service.impl;

import me.gogosing.component.PagesComponent;
import me.gogosing.model.Album;
import me.gogosing.model.Pages;
import me.gogosing.model.Pagination;
import me.gogosing.persistence.entity.AlbumEntity;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.AlbumRepository;
import me.gogosing.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static me.gogosing.consts.ApplicationConstants.DEFAULT_PAGE_LIMIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by JinBum Jeong on 2020/02/08.
 */
@ExtendWith(SpringExtension.class)
public class AlbumServiceImplTests {

    @MockBean
    private PagesComponent pagesComponent;

    @MockBean
    private AlbumRepository albumRepository;

    private AlbumService albumService;

    @BeforeEach
    public void init() {
        albumService = new AlbumServiceImpl(
                "/api/albums?page=", pagesComponent, albumRepository
        );
    }

    @Test
    public void testGetAlbumPagination() {
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

        albumEntity1.setSongEntities(Collections.singletonList(songEntity1));
        albumEntity2.setSongEntities(Collections.singletonList(songEntity2));

        ArgumentCaptor<String> localeArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        List<AlbumEntity> expectedContentResult = Arrays.asList(albumEntity1, albumEntity2).stream()
                .sorted(Comparator.comparing(AlbumEntity::getCreateOn).reversed())
                .collect(Collectors.toList());

        Page<AlbumEntity> expectedMockResult = PageableExecutionUtils.getPage(
                expectedContentResult,
                PageRequest.of(
                        0,
                        DEFAULT_PAGE_LIMIT
                ),
                expectedContentResult::size
        );

        when(
            albumRepository.getPaginatedAlbumEntities(
                    localeArgumentCaptor.capture(),
                    pageableArgumentCaptor.capture()
            )
        ).thenReturn(expectedMockResult);

        ArgumentCaptor<String> pathArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Page<AlbumEntity>> pageArgumentCaptor = ArgumentCaptor.forClass(Page.class);

        Pages expectedPagesResult = new Pages();
        expectedPagesResult.setFirst(null);
        expectedPagesResult.setPrev(null);
        expectedPagesResult.setLast("http://SERVER_URL/api/albums?page=2");
        expectedPagesResult.setNext("http://SERVER_URL/api/albums?page=2");

        when(
            pagesComponent.getPages(pathArgumentCaptor.capture(), pageArgumentCaptor.capture())
        ).thenReturn(expectedPagesResult);

        Pagination actualResult = albumService
                .getAlbumPagination(1, 10, Locale.KOREAN.getLanguage());

        assertThat(actualResult)
                .as("페이징 처리된 앨범 목록 조회 테스트")
                .isNotNull()
                .hasFieldOrProperty("statusCode")
                .hasFieldOrProperty("pages")
                .hasFieldOrProperty("albums");

        int statusCode = actualResult.getStatusCode();
        assertThat(statusCode)
                .as("페이징 수행 결과 검증")
                .isEqualTo(HttpStatus.OK.value());

        Pages pages = actualResult.getPages();
        assertThat(pages)
                .as("페이지 정보 결과 검증")
                .isNotNull()
                .isEqualTo(expectedPagesResult);

        List<Album> albums = actualResult.getAlbums();
        assertThat(albums)
                .as("페이징 결과 컨텐츠 검증")
                .isNotNull()
                .isNotEmpty()
                .hasSize(expectedContentResult.size())
                .extracting("id")
                .containsExactly(albumEntity2.getId(), albumEntity1.getId());
    }
}
