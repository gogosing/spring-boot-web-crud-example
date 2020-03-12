package me.gogosing.persistence.repository.custom;

import me.gogosing.persistence.entity.PlaylistInventoryEntity;

import java.util.List;

/**
 * Created by JinBum Jeong on 2020/03/12.
 */
public interface PlaylistInventoryRepositoryCustom {

    /**
     * 플레이리스트 컨텐츠 목록 조회.
     * @param userId 사용자 아이디.
     * @param playlistId 플레이리스트 레코드 대체 식별자.
     * @param inventoryId 컨텐츠 목록 레코드 식별자 목록.
     * @return 플레이리스트 컨텐츠 목록.
     */
    List<PlaylistInventoryEntity> getPlaylistInventoryEntities(
            String userId,
            String playlistId,
            List<String> inventoryId
    );
}
