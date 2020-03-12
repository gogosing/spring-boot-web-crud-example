package me.gogosing.service;

import me.gogosing.model.Playlist;

import java.util.List;

/**
 * 플레이리스트 서비스 Interface.
 * Created by JinBum Jeong on 2020/02/07.
 */
public interface PlaylistService {

    /**
     * 특정 사용자의 플레이리스트 생성.
     * @param userId 사용자 아이디.
     * @param title 플레이리스트 제목.
     * @return 생성된 플레이리스트 레코드 대체 식별자.
     */
    String createPlaylist(String userId, String title);

    /**
     * 특정 사용자의 플레이리스트 수정.
     * @param userId 사용자 아이디.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @param title 플레이리스트 제목.
     */
    void updatePlaylist(String userId, String id, String title);

    /**
     * 특정 사용자의 플레이리스트 삭제.
     * @param userId 사용자 아이디.
     * @param id 플레이리스트 레코드 대체 식별자.
     */
    void deletePlaylist(String userId, String id);

    /**
     * 특정 사용자의 플레이리스트 목록 조회.
     * @param userId 사용자 아이디.
     * @return 특정 사용자의 플레이리스트 목록.
     */
    List<Playlist> getPlaylist(String userId);
}
