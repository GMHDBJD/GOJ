package com.goj.restservice.projection;

import java.time.LocalDate;

import com.goj.restservice.entity.User;

public interface ContestDetail {
    Long getContestId();

    String getTitle();

    String getDescription();

    LocalDate getStartTime();

    LocalDate getEndTime();

    User getCreateUser();
}