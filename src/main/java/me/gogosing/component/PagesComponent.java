package me.gogosing.component;

import me.gogosing.model.Pages;
import org.springframework.data.domain.Page;

/**
 * 페이징 응답 정보 Component.
 * Created by JinBum Jeong on 2020/02/07.
 */
public interface PagesComponent {

    /**
     * 페이징 응답 정보 생성.
     * @param path 페이징 대상 path.
     * @param paginatedResult 페이징 결과.
     * @return 페이징 응답 정보.
     */
    <T> Pages getPages(String path, Page<T> paginatedResult);
}
