package com.goj.restservice.projection;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface ContestDetail {
    Long getContestId();

    String getTitle();

    String getDescription();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime getStartTime();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime getEndTime();

    @JsonProperty("createUser")
    String getCreateUserUsername();
}