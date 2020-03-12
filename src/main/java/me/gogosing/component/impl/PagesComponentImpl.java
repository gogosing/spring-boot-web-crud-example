package me.gogosing.component.impl;

import me.gogosing.component.PagesComponent;
import me.gogosing.model.Pages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Created by JinBum Jeong on 2020/02/07.
 */
@Component
public class PagesComponentImpl implements PagesComponent {

    private final String schema;

    private final String domain;

    /**
     * 페이징 URI 템플릿.
     */
    private final String PAGINATION_URI_TEMPLATE = "%s://%s%s%d";

    public PagesComponentImpl(
        @Value("${article.schema:https}")
        String schema,
        @Value("${article.domain:SERVER_URL}")
        String domain
    ) {
        this.schema = schema;
        this.domain = domain;
    }

    @Override
    public <T> Pages getPages(String path, Page<T> paginatedResult) {
        Pages pages = new Pages();

        if (paginatedResult.hasPrevious()) {
            Pageable first = paginatedResult.previousOrFirstPageable();
            pages.setFirst(getPaginationURI(path, first.getPageNumber()));
        }

        if (paginatedResult.hasPrevious()) {
            Pageable previous = paginatedResult.previousPageable();
            pages.setPrev(getPaginationURI(path, previous.getPageNumber()));
        }

        if (paginatedResult.hasNext()) {
            Pageable next = paginatedResult.nextPageable();
            pages.setNext(getPaginationURI(path, next.getPageNumber()));
        }

        if (paginatedResult.hasNext()) {
            Pageable last = paginatedResult.nextOrLastPageable();
            pages.setLast(getPaginationURI(path, last.getPageNumber()));
        }

        return pages;
    }

    /**
     * 앨범 페이징 URI 생성.
     * @param path 페이징 대상 path.
     * @param page 페이지 번호.
     * @return 페이징 URI.
     */
    private String getPaginationURI(String path, int page) {
        return String.format(PAGINATION_URI_TEMPLATE, schema, domain, path, (page + 1));
    }
}
