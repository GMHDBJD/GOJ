package com.goj.restservice.projection;

import java.time.LocalDate;

public interface ContestDetail {
    Long getContestId();

    String getTitle();

    String getDescription();

    LocalDate getStartTime();

    LocalDate getEndTime();

    String getCreateUserUsername();
}