package me.gogosing.persistence.entity;

import me.gogosing.persistence.repository.AlbumRepository;
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
public class AlbumEntityTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    public void testEntityProperties() {
        AlbumEntity albumEntity = new AlbumEntity();

        assertThat(albumEntity).as("프로퍼티 존재 여부 확인")
                .hasFieldOrProperty("key")
                .hasFieldOrProperty("id")
                .hasFieldOrProperty("title")
                .hasFieldOrProperty("deleted")
                .hasFieldOrProperty("createOn")
                .hasFieldOrProperty("songEntities")
                .hasFieldOrProperty("albumLocaleEntities");
    }

    @Test
    public void testStoreEntity() {
        AlbumEntity albumEntity = new AlbumEntity();

        albumEntity.setId("FA200205999999");
        albumEntity.setTitle("album title");
        albumEntity.setDeleted(false);
        albumEntity.setCreateOn(ZonedDateTime.now());

        entityManager.persist(albumEntity);

        Optional<AlbumEntity> actualEntity = albumRepository.findById(albumEntity.getKey());

        Assertions.assertThat(actualEntity)
                .as("앨범 저장 여부 확인")
                .isPresent();
    }
}
