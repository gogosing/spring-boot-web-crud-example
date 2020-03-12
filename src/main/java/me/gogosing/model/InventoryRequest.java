package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 플레이리스트 음원 적용 요청 모델.
 * Created by JinBum Jeong on 2020/02/07.
 */
public class InventoryRequest {

    /**
     * 적용할 앨범 레코드 대체 식별자 목록.
     */
    @JsonProperty(value = "albums")
    private List<String> albums;

    /**
     * 적용할 음원 레코드 대체 식별자 목록.
     */
    @JsonProperty(value = "songs")
    private List<String> songs;

    public List<String> getAlbums() {
        return albums;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }
}
