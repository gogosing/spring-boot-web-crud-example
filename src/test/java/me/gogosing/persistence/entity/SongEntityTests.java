package me.gogosing.persistence.entity;

import me.gogosing.persistence.repository.SongRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by JinBum Jeong on 2020/02/08.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SongEntityTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SongRepository songRepository;

    @Test
    public void testEntityProperties() {
        SongEntity songEntity = new SongEntity();

        assertThat(songEntity).as("프로퍼티 존재 여부 확인")
                .hasFieldOrProperty("key")
                .hasFieldOrProperty("id")
                .hasFieldOrProperty("albumEntity")
                .hasFieldOrProperty("title")
                .hasFieldOrProperty("length")
                .hasFieldOrProperty("trackNo")
                .hasFieldOrProperty("deleted")
                .hasFieldOrProperty("createOn");
    }

    @Test
    public void testStoreEntity() {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setId("FA200205999999");
        albumEntity.setTitle("album title");
        albumEntity.setDeleted(false);
        albumEntity.setCreateOn(ZonedDateTime.now());

        entityManager.persist(albumEntity);

        SongEntity songEntity = new SongEntity();
        songEntity.setId("FA200205999999");
        songEntity.setAlbumEntity(albumEntity);
        songEntity.setTitle("song title");
        songEntity.setLength(420);
        songEntity.setTrackNo(1);
        songEntity.setDeleted(false);
        songEntity.setCreateOn(ZonedDateTime.now());

        entityManager.persist(songEntity);

        Optional<SongEntity> actualEntity = songRepository.findById(songEntity.getKey());

        Assertions.assertThat(actualEntity)
                .as("음원 저장 여부 확인")
                .isPresent().get()
                .extracting("albumEntity")
                    .isNotNull();
    }
}
