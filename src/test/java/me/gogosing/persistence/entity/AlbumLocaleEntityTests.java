package me.gogosing.persistence.entity;

import me.gogosing.persistence.repository.AlbumLocaleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by JinBum Jeong on 2020/02/08.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AlbumLocaleEntityTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AlbumLocaleRepository albumLocaleRepository;

    @Test
    public void testEntityProperties() {
        AlbumLocaleEntity albumLocaleEntity = new AlbumLocaleEntity();

        assertThat(albumLocaleEntity).as("프로퍼티 존재 여부 확인")
                .hasFieldOrProperty("key")
                .hasFieldOrProperty("albumEntity")
                .hasFieldOrProperty("localeCode")
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

        AlbumLocaleEntity albumLocaleEntity = new AlbumLocaleEntity();
        albumLocaleEntity.setAlbumEntity(albumEntity);
        albumLocaleEntity.setLocaleCode(Locale.KOREA.getLanguage());
        albumLocaleEntity.setCreateOn(ZonedDateTime.now());

        entityManager.persist(albumLocaleEntity);

        Optional<AlbumLocaleEntity> actualEntity = albumLocaleRepository
                .findById(albumLocaleEntity.getKey());

        Assertions.assertThat(actualEntity)
                .as("앨범 서비스 가능 지역 저장 여부 확인")
                .isPresent().get()
                .extracting("albumEntity")
                .isNotNull();
    }
}
