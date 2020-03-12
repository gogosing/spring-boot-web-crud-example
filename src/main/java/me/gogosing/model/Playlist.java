package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * 플레이리스트 정보 모델.
 * Created by JinBum Jeong on 2020/02/07.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Playlist {

    /**
     * 플레이리스트 식별자.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * 플레이리스트 제목.
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * 플레이리스트 수록곡 목록.
     */
    @JsonProperty(value = "songs")
    private List<Song> songs;

    /**
     * 플레이리스트 생성일시.
     */
    @JsonProperty(value = "createOn")
    private ZonedDateTime createOn;

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

    public ZonedDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(ZonedDateTime createOn) {
        this.createOn = createOn;
    }
}
