package me.gogosing.persistence.repository.custom.impl;

import com.querydsl.jpa.JPQLQuery;
import me.gogosing.persistence.entity.AlbumEntity;
import me.gogosing.persistence.entity.QAlbumEntity;
import me.gogosing.persistence.entity.QAlbumLocaleEntity;
import me.gogosing.persistence.entity.QSongEntity;
import me.gogosing.persistence.repository.custom.AlbumRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static me.gogosing.consts.ApplicationConstants.ALBUM_SERVICE_AVAILABLE_ALL_LOCALE_CODE;

/**
 * Created by JinBum Jeong on 2020/03/12.
 */
public class AlbumRepositoryCustomImpl extends QuerydslRepositorySupport implements AlbumRepositoryCustom {

    public AlbumRepositoryCustomImpl() {
        super(AlbumEntity.class);
    }

    @Override
    public List<AlbumEntity> getAlbumEntities(String title, String locale) {
        QAlbumEntity albumEntity = QAlbumEntity.albumEntity;
        QAlbumLocaleEntity albumLocaleEntity = QAlbumLocaleEntity.albumLocaleEntity;
        QSongEntity songEntity = QSongEntity.songEntity;

        JPQLQuery<AlbumEntity> query = from(albumEntity)
                .leftJoin(albumEntity.albumLocaleEntities, albumLocaleEntity)
                .leftJoin(albumEntity.songEntities, songEntity)
                .fetchJoin()
                .where(
                        albumEntity.deleted.isFalse(),
                        albumEntity.title.containsIgnoreCase(title),
                        albumLocaleEntity.localeCode.in(ALBUM_SERVICE_AVAILABLE_ALL_LOCALE_CODE, locale)
                );

        return query.orderBy(albumEntity.title.asc()).fetch();
    }

    @Override
    public List<AlbumEntity> getAlbumEntities(String locale, List<String> albums) {
        QAlbumEntity albumEntity = QAlbumEntity.albumEntity;
        QAlbumLocaleEntity albumLocaleEntity = QAlbumLocaleEntity.albumLocaleEntity;
        QSongEntity songEntity = QSongEntity.songEntity;

        JPQLQuery<AlbumEntity> query = from(albumEntity)
                .leftJoin(albumEntity.albumLocaleEntities, albumLocaleEntity)
                .leftJoin(albumEntity.songEntities, songEntity)
                .fetchJoin()
                .where(
                        albumEntity.deleted.isFalse(),
                        albumEntity.id.in(albums),
                        albumLocaleEntity.localeCode.in(ALBUM_SERVICE_AVAILABLE_ALL_LOCALE_CODE, locale)
                );

        return query.orderBy(albumEntity.title.asc()).fetch();
    }

    @Override
    public Page<AlbumEntity> getPaginatedAlbumEntities(String locale, Pageable pageable) {
        QAlbumEntity albumEntity = QAlbumEntity.albumEntity;
        QAlbumLocaleEntity albumLocaleEntity = QAlbumLocaleEntity.albumLocaleEntity;

        JPQLQuery<AlbumEntity> query = from(albumEntity)
                .leftJoin(albumEntity.albumLocaleEntities, albumLocaleEntity)
                .where(
                        albumEntity.deleted.isFalse(),
                        albumLocaleEntity.localeCode.in(ALBUM_SERVICE_AVAILABLE_ALL_LOCALE_CODE, locale)
                );

        List<AlbumEntity> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(albumEntity.createOn.desc())
                .fetch();

        long totalCount = 0L;
        if (!results.isEmpty()) {
            totalCount = query.select(albumEntity).distinct().fetchCount();
        }

        return new PageImpl<>(results, pageable, totalCount);
    }
}
