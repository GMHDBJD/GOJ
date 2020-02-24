package com.goj.restservice.security;

import java.util.Optional;

import com.goj.restservice.entity.User;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof User)
            return Optional.of(((User) object).getUsername());
        else
            return Optional.empty();

    }
}