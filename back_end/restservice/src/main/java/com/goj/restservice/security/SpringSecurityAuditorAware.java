package com.goj.restservice.security;

import java.util.Optional;

import com.goj.restservice.entity.User;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditorAware implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof User)
            return Optional.of((User) object);
        else
            return Optional.empty();

    }
}