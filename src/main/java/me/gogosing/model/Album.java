package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 앨범 정보 모델.
 * Created by JinBum Jeong on 2020/02/06.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Album {

    /**
     * 앨범 레코드 대체 식별자.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * 제목.
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * 수록곡 목록.
     */
    @JsonProperty(value = "songs")
    private List<Song> songs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
