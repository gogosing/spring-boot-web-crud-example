package me.gogosing.web.servlet;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Request Header 로 전달되는 X-Locale-With 를 이용하여
 * Locale 를 결정하도록 하는 LocaleResolver.
 * Created by JinBum Jeong on 2020/02/08.
 */
public class LocaleWithHeaderLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerValue = request.getHeader("X-Locale-With");

        Locale locale = Locale.KOREAN;
        if (headerValue != null) {
            switch (headerValue.toLowerCase()) {
                case "ko":  locale = Locale.KOREAN;     break;
                case "en":  locale = Locale.ENGLISH;    break;
                case "ja":  locale = Locale.JAPANESE;   break;
            }
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new UnsupportedOperationException("Cannot change locale");
    }
}
