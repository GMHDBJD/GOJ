package com.goj.restservice.config;

import com.goj.restservice.entity.User;
import com.goj.restservice.security.SpringSecurityAuditorAware;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditorAwareConfig {

  @Bean
  public AuditorAware<User> auditorProvider() {
    return new SpringSecurityAuditorAware();
  }
}