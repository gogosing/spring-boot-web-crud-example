package me.gogosing.config;

import me.gogosing.web.servlet.LocaleWithHeaderLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Created by JinBum Jeong on 2020/02/08.
 */
@Configuration
public class LocaleConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        return new LocaleWithHeaderLocaleResolver();
    }
}
