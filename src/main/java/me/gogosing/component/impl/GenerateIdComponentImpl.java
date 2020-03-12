package me.gogosing.component.impl;

import me.gogosing.component.GenerateIdComponent;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by JinBum Jeong on 2020/02/07.
 */
@Component
public class GenerateIdComponentImpl implements GenerateIdComponent {

    private final int playlistIdLength = 14;

    private final int inventoryIdLength = 14;

    @Override
    public String playlistId() {
        return generateId("FP", "yyyyMMdd", playlistIdLength);
    }

    @Override
    public String inventoryId() {
        return generateId("FI", "yyyyMMdd", inventoryIdLength);
    }

    /**
     * 고유 식별자 생성.
     * @param prefix 식별자 접두사.
     * @param dateFormat 식별자 본문에 포함될 날짜 형식.
     * @param length 식별자 생성 길이.
     * @return 생성된 고유 식별자.
     */
    private String generateId(String prefix, String dateFormat, int length) {
        String currentDate = "";
        if (dateFormat != null) {
            currentDate = ZonedDateTime.now()
                .format(DateTimeFormatter.ofPattern(dateFormat));
        }

        int suffixLength = length - (prefix.length() + currentDate.length());

        String randomString = RandomStringUtils
            .randomAlphanumeric(suffixLength).toUpperCase();

        return new StringBuilder(prefix)
                .append(currentDate).append(randomString).toString();
    }
}
