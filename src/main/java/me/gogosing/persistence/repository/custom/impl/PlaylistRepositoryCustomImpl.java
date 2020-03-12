package me.gogosing.persistence.repository.custom.impl;

import me.gogosing.persistence.entity.PlaylistEntity;
import me.gogosing.persistence.entity.QPlaylistEntity;
import me.gogosing.persistence.entity.QPlaylistInventoryEntity;
import me.gogosing.persistence.entity.QSongEntity;
import me.gogosing.persistence.repository.custom.PlaylistRepositoryCustom;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

/**
 * Created by JinBum Jeong on 2020/03/12.
 */
public class PlaylistRepositoryCustomImpl extends QuerydslRepositorySupport implements PlaylistRepositoryCustom {

    public PlaylistRepositoryCustomImpl() {
        super(PlaylistEntity.class);
    }

    @Override
    public List<PlaylistEntity> getPlaylistEntities(String userId) {
        QPlaylistEntity playlistEntity = QPlaylistEntity.playlistEntity;
        QPlaylistInventoryEntity playlistInventoryEntity = QPlaylistInventoryEntity.playlistInventoryEntity;
        QSongEntity songEntity = QSongEntity.songEntity;

        JPQLQuery<PlaylistEntity> query = from(playlistEntity)
                .leftJoin(playlistEntity.playlistInventoryEntities, playlistInventoryEntity)
                .fetchJoin()
                .innerJoin(playlistInventoryEntity.songEntity, songEntity)
                .fetchJoin()
                .where(
                        playlistEntity.createBy.eq(userId)
                );

        return query.orderBy(playlistEntity.createOn.desc()).fetch();
    }

    @Override
    public Optional<PlaylistEntity> getPlaylistEntity(String userId, String playlistId) {
        QPlaylistEntity playlistEntity = QPlaylistEntity.playlistEntity;

        return Optional.ofNullable(
                from(playlistEntity)
                .where(
                        playlistEntity.id.eq(playlistId),
                        playlistEntity.createBy.eq(userId)
                )
                .fetchOne()
        );
    }

    @Override
    public Optional<PlaylistEntity> getPlaylistEntityWithSongEntities(String userId, String playlistId) {
        QPlaylistEntity playlistEntity = QPlaylistEntity.playlistEntity;
        QPlaylistInventoryEntity playlistInventoryEntity = QPlaylistInventoryEntity.playlistInventoryEntity;
        QSongEntity songEntity = QSongEntity.songEntity;

        return Optional.ofNullable(
                from(playlistEntity)
                .leftJoin(playlistEntity.playlistInventoryEntities, playlistInventoryEntity)
                .innerJoin(playlistInventoryEntity.songEntity, songEntity)
                .where(
                        playlistEntity.id.eq(playlistId),
                        playlistEntity.createBy.eq(userId)
                )
                .fetchOne()
        );
    }
}
