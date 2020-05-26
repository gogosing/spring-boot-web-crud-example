package me.gogosing.controller;

import me.gogosing.model.Pagination;
import me.gogosing.model.LocaleCode;
import me.gogosing.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 앨범 Controller.
 * Created by JinBum Jeong on 2020/02/05.
 */
@RestController
public class AlbumController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * 앨범 목록 조회.
     * @param page 요청 page 번호.
     * @param size 페이지당 노출 갯수.
     * @param locale 검색을 요청하는 사용자의 지역.
     * @return 페이징 처리된 앨범 목록.
     */
    @GetMapping("/albums")
    public Pagination getAlbumPagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "locale") LocaleCode locale
    ) {
        Pagination pagination;
        try {
            pagination = albumService.getAlbumPagination(page, size, locale.getCode());
        } catch (Exception exception) {
            pagination = new Pagination(INTERNAL_SERVER_ERROR.value());
            LOGGER.error("앨범 목록을 조회하던 중 오류가 발생하였습니다.", exception);
        }
        return pagination;
    }
}
