package com.goj.restservice.util;

import com.goj.restservice.exception.CustomException;
import com.goj.restservice.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Util {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public <T> T checkResourceFound(final T resource) {
        if (resource == null) {
            throw new CustomException("Resource Not Found", HttpStatus.NOT_FOUND);
        }
        return resource;
    }
}