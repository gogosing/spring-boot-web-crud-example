package me.gogosing.consts;

/**
 * 전역 상수 모음 클래스.
 * Created by JinBum Jeong on 2020/02/07.
 */
public class ApplicationConstants {

    private ApplicationConstants() {
        throw new IllegalAccessError("Application 에서 공통으로 사용되는 상수 클래스로 생성자를 사용할 수 없습니다.");
    }

    /**
     * 페이지당 게시물 기본 단위.
     */
    public static final int DEFAULT_PAGE_LIMIT = 10;

    /**
     * 앨범 서비스 가능 지역이 '전체'인 경우의 localeCode.
     */
    public static final String ALBUM_SERVICE_AVAILABLE_ALL_LOCALE_CODE = "all";
}
