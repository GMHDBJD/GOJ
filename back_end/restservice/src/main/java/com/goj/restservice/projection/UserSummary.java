package com.goj.restservice.projection;

public interface UserSummary {

    String getUsername();

    String getNickname();

    Long getSubmit();

    Long getAccepted();

    Integer getRatio();
}