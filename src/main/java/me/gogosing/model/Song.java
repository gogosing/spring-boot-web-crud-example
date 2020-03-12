package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 곡 정보 모델.
 * Created by JinBum Jeong on 2020/02/06.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Song {

    /**
     * 곡 레코드 대체 식별자.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * 제목.
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * 트랙 번호.
     */
    @JsonProperty(value = "track")
    private int track;

    /**
     * 길이.
     */
    @JsonProperty(value = "length")
    private int length;

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

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
