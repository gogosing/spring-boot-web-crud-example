package me.gogosing.converter;

import me.gogosing.model.LocaleCode;
import org.springframework.core.convert.converter.Converter;

/**
 * 서비스 가능 지역 코드를 열거형으로 변환해주는 converter.
 * Created by JinBum Jeong on 2020/02/06.
 */
public class LocaleCodeToEnumConverter implements Converter<String, LocaleCode> {

    @Override
    public LocaleCode convert(String source) {
        return LocaleCode.valueOf(source.toUpperCase());
    }
}
