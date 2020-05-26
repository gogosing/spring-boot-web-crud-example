package me.gogosing.persistence.entity;

import me.gogosing.config.JpaConfiguration;
import me.gogosing.persistence.repository.PlaylistInventoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by JinBum Jeong on 2020/02/08.
 */
@DataJpaTest
@Import(JpaConfiguration.class)
@ExtendWith(SpringExtension.class)
public class PlaylistInventoryEntityTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlaylistInventoryRepository playlistInventoryRepository;

    @Test
    public void testEntityProperties() {
        PlaylistInventoryEntity playlistInventoryEntity = new PlaylistInventoryEntity();

        assertThat(playlistInventoryEntity).as("프로퍼티 존재 여부 확인")
                .hasFieldOrProperty("key")
                .hasFieldOrProperty("id")
                .hasFieldOrProperty("playlistEntity")
                .hasFieldOrProperty("songEntity")
                .hasFieldOrProperty("createOn");
    }

    @Test
    public void testStoreSomeEntity() {
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

        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId("FP20200208IMR7");
        playlistEntity.setTitle("playlist title");
        playlistEntity.setCreateBy("FU200205999999");
        playlistEntity.setCreateOn(ZonedDateTime.now());

        entityManager.persist(playlistEntity);

        PlaylistInventoryEntity playlistInventoryEntity = new PlaylistInventoryEntity();

        playlistInventoryEntity.setId("FI20200208FXGD");
        playlistInventoryEntity.setPlaylistEntity(playlistEntity);
        playlistInventoryEntity.setSongEntity(songEntity);
        playlistInventoryEntity.setCreateOn(ZonedDateTime.now());

        entityManager.persist(playlistInventoryEntity);

        Optional<PlaylistInventoryEntity> actualEntity = playlistInventoryRepository
                .findById(playlistInventoryEntity.getKey());

        Assertions.assertThat(actualEntity)
                .as("플레이리스트 인벤토리 저장 여부 확인")
                .isPresent();

        Assertions.assertThat(actualEntity)
                .as("플레이리스트 저장 여부 확인")
                .get()
                .extracting("playlistEntity")
                .isNotNull();

        Assertions.assertThat(actualEntity)
                .as("음원 저장 여부 확인")
                .get()
                .extracting("songEntity")
                .isNotNull();
    }
}
