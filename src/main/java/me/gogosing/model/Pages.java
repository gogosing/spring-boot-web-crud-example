package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 페이징 응답 정보 모델.
 * Created by JinBum Jeong on 2020/02/07.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pages {

    /**
     * 결과의 첫 페이지 URI.
     */
    @JsonProperty(value = "first")
    private String first;

    /**
     * 현재 response의 이전 페이지 URI.
     */
    @JsonProperty(value = "prev")
    private String prev;

    /**
     * 현재 response의 다음 페이지 URI.
     */
    @JsonProperty(value = "last")
    private String last;

    /**
     * 결과의 마지막 페이지 URI.
     */
    @JsonProperty(value = "next")
    private String next;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
