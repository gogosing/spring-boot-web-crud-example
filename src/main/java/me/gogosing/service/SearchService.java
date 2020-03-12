package me.gogosing.service;

import me.gogosing.model.Album;
import me.gogosing.model.Song;

import java.util.List;

/**
 * 통합 검색 서비스 Interface.
 * Created by JinBum Jeong on 2020/02/06.
 */
public interface SearchService {

    /**
     * 앨범 목록 조회.
     * @param title 검색어.
     * @param locale 검색 지역 코드.
     * @return 검색 결과 목록.
     */
    List<Album> getAlbumList(String title, String locale);

    /**
     * 곡 목록 조회.
     * @param title 검색어.
     * @param locale 검색 지역 코드.
     * @return 검색 결과 목록.
     */
    List<Song> getSongList(String title, String locale);
}
