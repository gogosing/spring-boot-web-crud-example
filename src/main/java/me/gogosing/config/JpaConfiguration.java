package me.gogosing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = {"me.gogosing.persistence.repository"},
        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class
)
public class JpaConfiguration {
}
