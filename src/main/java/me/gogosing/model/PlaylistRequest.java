package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 플레이리스트 생성/수정 요청 정보 모델.
 * Created by JinBum Jeong on 2020/02/07.
 */
public class PlaylistRequest {

    /**
     * 플레이리스트 제목.
     */
    @JsonProperty(value = "title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
