package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

/**
 * 플레이리스트 컨텐츠 정보 모델.
 * Created by JinBum Jeong on 2020/02/07.
 */
public class Inventory {

    /**
     * 컨텐츠 레코드 대체 식별자.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * 컨텐츠 음원 상세 정보.
     */
    @JsonProperty(value = "song")
    private Song song;

    /**
     * 플레이 리스트 내, 컨텐츠 등록일.
     */
    @JsonProperty(value = "createOn")
    private ZonedDateTime createOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public ZonedDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(ZonedDateTime createOn) {
        this.createOn = createOn;
    }
}
