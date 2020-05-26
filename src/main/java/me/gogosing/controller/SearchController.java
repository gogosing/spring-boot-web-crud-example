package me.gogosing.controller;

import me.gogosing.model.LocaleCode;
import me.gogosing.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.AbstractMap.SimpleEntry;

/**
 * 통합 검색 Controller.
 * Created by JinBum Jeong on 2020/02/06.
 */
@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * 통합 검색.
     * @param title 검색어.
     * @param locale 검색을 요청하는 사용자의 지역.
     * @return 검색 결과.
     */
    @GetMapping("/search")
    public Map<String, Object> getSearchResult(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "locale") LocaleCode locale
    ) {
        return Collections.unmodifiableMap(Stream.of(
            new SimpleEntry<>("albums", searchService.getAlbumList(title, locale.getCode())),
            new SimpleEntry<>("songs", searchService.getSongList(title, locale.getCode()))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}
