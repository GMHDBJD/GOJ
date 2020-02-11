package com.goj.restservice.util;

import com.goj.restservice.exception.CustomException;

import org.springframework.http.HttpStatus;

public class Util {

    public static <T> T checkResourceFound(final T resource) {
        if (resource == null) {
            throw new CustomException("Resource Not Found", HttpStatus.NOT_FOUND);
        }
        return resource;
    }
}