package me.gogosing.persistence.repository.custom;

import me.gogosing.persistence.entity.SongEntity;

import java.util.List;

/**
 * Created by JinBum Jeong on 2020/03/12.
 */
public interface SongRepositoryCustom {

    /**
     * 곡 목록 조회.
     * @param title 곡 제목 검색어.
     * @param locale 앨범 서비스 지역 코드.
     * @return 곡 엔티티 목록.
     */
    List<SongEntity> getSongEntities(String title, String locale);

    /**
     * 곡 목록 조회.
     * @param locale 사용자의 지역 코드.
     * @param songs 조회 대상 음원 레코드 대체 식별자 목록.
     * @return 곡 엔티티 목록.
     */
    List<SongEntity> getSongEntities(String locale, List<String> songs);
}
