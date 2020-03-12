package me.gogosing.component.impl;

import me.gogosing.component.PagesComponent;
import me.gogosing.model.Pages;
import me.gogosing.persistence.entity.AlbumEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static me.gogosing.consts.ApplicationConstants.DEFAULT_PAGE_LIMIT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by JinBum Jeong on 2020/02/09.
 */
@ExtendWith(SpringExtension.class)
public class PagesComponentImplTests {

    private PagesComponent pagesComponent;

    @BeforeEach
    public void init() {
        String schema = "http";
        String domain = "SERVER_URL";
        pagesComponent = new PagesComponentImpl(schema, domain);
    }

    @Test
    public void testGetPages() {
        String expectedPath = "/api/albums?page=";

        AlbumEntity albumEntity1 = new AlbumEntity();
        albumEntity1.setId("FA200205999991");
        albumEntity1.setTitle("album title 1");
        albumEntity1.setDeleted(false);
        albumEntity1.setCreateOn(ZonedDateTime.now());

        AlbumEntity albumEntity2 = new AlbumEntity();
        albumEntity2.setId("FA200205999992");
        albumEntity2.setTitle("album title 2");
        albumEntity2.setDeleted(false);
        albumEntity2.setCreateOn(ZonedDateTime.now().minusDays(1));

        AlbumEntity albumEntity3 = new AlbumEntity();
        albumEntity3.setId("FA200205999993");
        albumEntity3.setTitle("album title 3");
        albumEntity3.setDeleted(false);
        albumEntity3.setCreateOn(ZonedDateTime.now().minusDays(2));

        AlbumEntity albumEntity4 = new AlbumEntity();
        albumEntity4.setId("FA200205999994");
        albumEntity4.setTitle("album title 4");
        albumEntity4.setDeleted(false);
        albumEntity4.setCreateOn(ZonedDateTime.now().minusDays(3));

        AlbumEntity albumEntity5 = new AlbumEntity();
        albumEntity5.setId("FA200205999995");
        albumEntity5.setTitle("album title 5");
        albumEntity5.setDeleted(false);
        albumEntity5.setCreateOn(ZonedDateTime.now().minusDays(4));

        AlbumEntity albumEntity6 = new AlbumEntity();
        albumEntity6.setId("FA200205999996");
        albumEntity6.setTitle("album title 6");
        albumEntity6.setDeleted(false);
        albumEntity6.setCreateOn(ZonedDateTime.now().minusDays(5));

        AlbumEntity albumEntity7 = new AlbumEntity();
        albumEntity7.setId("FA200205999997");
        albumEntity7.setTitle("album title 7");
        albumEntity7.setDeleted(false);
        albumEntity7.setCreateOn(ZonedDateTime.now().minusDays(6));

        AlbumEntity albumEntity8 = new AlbumEntity();
        albumEntity8.setId("FA200205999998");
        albumEntity8.setTitle("album title 8");
        albumEntity8.setDeleted(false);
        albumEntity2.setCreateOn(ZonedDateTime.now().minusDays(7));

        AlbumEntity albumEntity9 = new AlbumEntity();
        albumEntity9.setId("FA200205999999");
        albumEntity9.setTitle("album title 9");
        albumEntity9.setDeleted(false);
        albumEntity9.setCreateOn(ZonedDateTime.now().minusDays(8));

        AlbumEntity albumEntity10 = new AlbumEntity();
        albumEntity10.setId("FA200205999980");
        albumEntity10.setTitle("album title 10");
        albumEntity10.setDeleted(false);
        albumEntity10.setCreateOn(ZonedDateTime.now().minusDays(9));

        AlbumEntity albumEntity11 = new AlbumEntity();
        albumEntity11.setId("FA200205999981");
        albumEntity11.setTitle("album title 11");
        albumEntity11.setDeleted(false);
        albumEntity11.setCreateOn(ZonedDateTime.now().minusDays(10));

        AlbumEntity albumEntity12 = new AlbumEntity();
        albumEntity12.setId("FA200205999982");
        albumEntity12.setTitle("album title 12");
        albumEntity12.setDeleted(false);
        albumEntity12.setCreateOn(ZonedDateTime.now().minusDays(11));

        List<AlbumEntity> albumEntities = Arrays.asList(
                albumEntity1, albumEntity2, albumEntity3, albumEntity4, albumEntity5, albumEntity6,
                albumEntity7, albumEntity8, albumEntity9, albumEntity10, albumEntity11, albumEntity12
        );

        List<AlbumEntity> expectedContentResult = Arrays.asList(
                albumEntity1, albumEntity2, albumEntity3, albumEntity4, albumEntity5, albumEntity6,
                albumEntity7, albumEntity8, albumEntity9, albumEntity10
        );

        Page<AlbumEntity> expectedPaginatedResult = PageableExecutionUtils.getPage(
                expectedContentResult,
                PageRequest.of(
                        0,
                        DEFAULT_PAGE_LIMIT
                ),
                albumEntities::size
        );

        Pages actualResult = pagesComponent.getPages(expectedPath, expectedPaginatedResult);

        Pages expectedPages = new Pages();
        expectedPages.setFirst(null);
        expectedPages.setPrev(null);
        expectedPages.setLast("http://SERVER_URL/api/albums?page=2");
        expectedPages.setNext("http://SERVER_URL/api/albums?page=2");

        assertThat(actualResult)
                .as("페이징 정보 검증")
                .isNotNull()
                .hasFieldOrPropertyWithValue("first", expectedPages.getFirst())
                .hasFieldOrPropertyWithValue("prev", expectedPages.getPrev())
                .hasFieldOrPropertyWithValue("last", expectedPages.getLast())
                .hasFieldOrPropertyWithValue("next", expectedPages.getNext());
    }
}
