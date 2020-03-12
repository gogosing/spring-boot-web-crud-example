package me.gogosing.persistence.repository.custom.impl;

import me.gogosing.persistence.entity.PlaylistInventoryEntity;
import me.gogosing.persistence.entity.QPlaylistEntity;
import me.gogosing.persistence.entity.QPlaylistInventoryEntity;
import me.gogosing.persistence.entity.QSongEntity;
import me.gogosing.persistence.repository.custom.PlaylistInventoryRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

/**
 * Created by JinBum Jeong on 2020/03/12.
 */
public class PlaylistInventoryRepositoryCustomImpl
        extends QuerydslRepositorySupport implements PlaylistInventoryRepositoryCustom {

    public PlaylistInventoryRepositoryCustomImpl() {
        super(PlaylistInventoryEntity.class);
    }

    @Override
    public List<PlaylistInventoryEntity> getPlaylistInventoryEntities(
            String userId,
            String playlistId,
            List<String> inventoryId
    ) {
        QPlaylistInventoryEntity playlistInventoryEntity = QPlaylistInventoryEntity.playlistInventoryEntity;
        QPlaylistEntity playlistEntity = QPlaylistEntity.playlistEntity;
        QSongEntity songEntity = QSongEntity.songEntity;

        return from(playlistInventoryEntity)
                .innerJoin(playlistInventoryEntity.playlistEntity, playlistEntity)
                .fetchJoin()
                .innerJoin(playlistInventoryEntity.songEntity, songEntity)
                .fetchJoin()
                .where(
                        playlistInventoryEntity.id.in(inventoryId),
                        playlistEntity.id.eq(playlistId),
                        playlistEntity.createBy.eq(userId)
                )
                .fetch();
    }
}
