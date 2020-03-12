package me.gogosing.persistence.repository.custom.impl;

import me.gogosing.persistence.entity.QAlbumEntity;
import me.gogosing.persistence.entity.QAlbumLocaleEntity;
import me.gogosing.persistence.entity.QSongEntity;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.custom.SongRepositoryCustom;
import com.querydsl.jpa.JPQLQuery;
import me.gogosing.consts.ApplicationConstants;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

/**
 * Created by JinBum Jeong on 2020/03/12.
 */
public class SongRepositoryCustomImpl extends QuerydslRepositorySupport implements SongRepositoryCustom {

    public SongRepositoryCustomImpl() {
        super(SongEntity.class);
    }

    @Override
    public List<SongEntity> getSongEntities(String title, String locale) {
        QSongEntity songEntity = QSongEntity.songEntity;
        QAlbumEntity albumEntity = QAlbumEntity.albumEntity;
        QAlbumLocaleEntity albumLocaleEntity = QAlbumLocaleEntity.albumLocaleEntity;

        JPQLQuery<SongEntity> query = from(songEntity)
                .innerJoin(songEntity.albumEntity, albumEntity)
                .fetchJoin()
                .leftJoin(albumEntity.albumLocaleEntities, albumLocaleEntity)
                .fetchJoin()
                .where(
                        songEntity.deleted.isFalse(),
                        albumEntity.deleted.isFalse(),
                        songEntity.title.containsIgnoreCase(title),
                        albumLocaleEntity.localeCode.in(ApplicationConstants.ALBUM_SERVICE_AVAILABLE_ALL_LOCALE_CODE, locale)
                );

        return query.orderBy(songEntity.title.asc()).fetch();
    }

    @Override
    public List<SongEntity> getSongEntities(String locale, List<String> songs) {
        QSongEntity songEntity = QSongEntity.songEntity;
        QAlbumEntity albumEntity = QAlbumEntity.albumEntity;
        QAlbumLocaleEntity albumLocaleEntity = QAlbumLocaleEntity.albumLocaleEntity;

        JPQLQuery<SongEntity> query = from(songEntity)
                .innerJoin(songEntity.albumEntity, albumEntity)
                .fetchJoin()
                .leftJoin(albumEntity.albumLocaleEntities, albumLocaleEntity)
                .fetchJoin()
                .where(
                        songEntity.deleted.isFalse(),
                        albumEntity.deleted.isFalse(),
                        songEntity.id.in(songs),
                        albumLocaleEntity.localeCode.in(ApplicationConstants.ALBUM_SERVICE_AVAILABLE_ALL_LOCALE_CODE, locale)
                );

        return query.orderBy(songEntity.title.asc()).fetch();
    }
}
