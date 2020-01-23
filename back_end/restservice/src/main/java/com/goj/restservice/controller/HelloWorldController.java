package com.goj.restservice.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goj.restservice.entity.HelloWorld;

@RestController
public class HelloWorldController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/helloworld")
    public HelloWorld helloworld(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new HelloWorld(counter.incrementAndGet(), String.format(template, name));
    }
}