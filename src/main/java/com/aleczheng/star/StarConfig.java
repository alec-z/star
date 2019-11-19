package com.aleczheng.star;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.aleczheng.star.dao")
@EnableJpaAuditing
public class StarConfig {
}
