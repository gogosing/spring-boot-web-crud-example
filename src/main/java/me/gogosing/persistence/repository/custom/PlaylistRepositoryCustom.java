package me.gogosing.persistence.repository.custom;

import me.gogosing.persistence.entity.PlaylistEntity;

import java.util.List;
import java.util.Optional;

/**
 * Created by JinBum Jeong on 2020/03/12.
 */
public interface PlaylistRepositoryCustom {

    /**
     * 플레이리스트 목록 조회.
     * @param userId 플레이리스트를 소유한 사용자 레코드 대체 식별자.
     * @return 플레이리스트 목록.
     */
    List<PlaylistEntity> getPlaylistEntities(String userId);

    /**
     * 특정 플레이리스트 정보 조회.
     * @param userId 사용자 아이디.
     * @param playlistId 플레이리스트 레코드 대체 식별자.
     * @return 플레이리스트 정보.
     */
    Optional<PlaylistEntity> getPlaylistEntity(String userId, String playlistId);

    /**
     * 특정 플레이리스트 정보 조회.(수록곡이 포함된)
     * @param userId 사용자 아이디.
     * @param playlistId 플레이리스트 레코드 대체 식별자.
     * @return 플레이리스트 정보.
     */
    Optional<PlaylistEntity> getPlaylistEntityWithSongEntities(String userId, String playlistId);
}
