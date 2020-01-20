package com.goj.restservice;

/**
 * HelloWorld
 */
public class HelloWorld {

    private long id;
    private String message;

    public HelloWorld(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}