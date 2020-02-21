package com.goj.restservice.projection;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ContestDetail {
    Long getContestId();

    String getTitle();

    String getDescription();

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();

    @JsonProperty("createUser")
    String getCreateUserUsername();
}