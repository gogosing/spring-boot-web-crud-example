package me.gogosing.persistence.entity;

import me.gogosing.persistence.repository.PlaylistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by JinBum Jeong on 2020/02/08.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PlaylistEntityTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    public void testEntityProperties() {
        PlaylistEntity playlistEntity = new PlaylistEntity();

        assertThat(playlistEntity).as("프로퍼티 존재 여부 확인")
                .hasFieldOrProperty("key")
                .hasFieldOrProperty("id")
                .hasFieldOrProperty("title")
                .hasFieldOrProperty("createBy")
                .hasFieldOrProperty("createOn")
                .hasFieldOrProperty("playlistInventoryEntities");
    }

    @Test
    public void testStoreSomeEntity() {
        PlaylistEntity playlistEntity = new PlaylistEntity();

        playlistEntity.setId("FP20200208IMR7");
        playlistEntity.setTitle("playlist title");
        playlistEntity.setCreateBy("FU200205999999");
        playlistEntity.setCreateOn(ZonedDateTime.now());

        entityManager.persist(playlistEntity);

        Optional<PlaylistEntity> actualEntity = playlistRepository.findById(playlistEntity.getKey());

        Assertions.assertThat(actualEntity)
                .as("플레이리스트 저장 여부 확인")
                .isPresent();
    }
}
