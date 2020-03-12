package me.gogosing.service;

import me.gogosing.model.Pagination;

/**
 * 앨범 서비스 Interface.
 * Created by JinBum Jeong on 2020/02/07.
 */
public interface AlbumService {

    /**
     * 페이징 처리된 앨범 목록 조회.
     * @param page 요청 page 번호.
     * @param size 페이지당 노출 갯수.
     * @param locale 검색을 요청하는 사용자의 지역.
     * @return 페이징 처리된 앨범 목록.
     */
    Pagination getAlbumPagination(int page, int size, String locale);
}
