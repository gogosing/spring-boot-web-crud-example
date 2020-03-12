package me.gogosing.model;

/**
 * 서비스 지역 코드 enum.
 * Created by JinBum Jeong on 2020/02/06.
 */
public enum LocaleCode {

    KO("ko"), /* 한국 */
    JA("ja"), /* 일본 */
    EN("en"); /* 미국 */

    String code;

    LocaleCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
