package me.gogosing.exception;

/**
 * 조회하고자 하는 데이터가 존재하지 않을 시 발생하는 exception.
 * Created by JinBum Jeong on 2020/02/06.
 */
public class NotFoundException extends RuntimeException {

    /**
     * 생성자.
     * @param message 예외 메시지
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * 생성자.
     * @param message 예외 메시지
     * @param cause 예외 원인
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
