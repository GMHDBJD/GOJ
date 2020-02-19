package com.goj.restservice.projection;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ContestDetail {
    Long getContestId();

    String getTitle();

    String getDescription();

    LocalDate getStartTime();

    LocalDate getEndTime();

    @JsonProperty("username")
    String getCreateUserUsername();
}