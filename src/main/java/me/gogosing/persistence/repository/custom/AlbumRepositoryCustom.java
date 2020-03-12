package me.gogosing.persistence.repository.custom;

import me.gogosing.persistence.entity.AlbumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by JinBum Jeong on 2020/03/12.
 */
public interface AlbumRepositoryCustom {

    /**
     * 앨범 목록 조회.
     * @param title 앨범 타이틀 검색어.
     * @param locale 앨범 서비스 지역 코드.
     * @return 앨범 엔티티 목록.
     */
    List<AlbumEntity> getAlbumEntities(String title, String locale);

    /**
     * 앨범 목록 조회.
     * @param locale 앨범 서비스 지역 코드.
     * @param albums 앨범 레코드 대체 식별자 목록.
     * @return 앨범 엔티티 목록.
     */
    List<AlbumEntity> getAlbumEntities(String locale, List<String> albums);

    /**
     * 페이징 처리된 앨범 목록 조회.
     * @param locale 앨범 서비스 지역 코드.
     * @param pageable 페이징 조건.
     * @return 페이징 처리된 앨범 목록.
     */
    Page<AlbumEntity> getPaginatedAlbumEntities(String locale, Pageable pageable);
}
