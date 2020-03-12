package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * 페이징 결과 정보 모델.
 * Created by JinBum Jeong on 2020/02/07.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination {

    public Pagination() {}

    public Pagination(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * 페이징 수형 결과.
     */
    @JsonProperty(value = "statusCode")
    private int statusCode = HttpStatus.OK.value();

    /**
     * 페이징 정보.
     */
    @JsonProperty(value = "pages")
    private Pages pages;

    /**
     * 앨범 목록.
     */
    @JsonProperty(value = "albums")
    private List<Album> albums;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
