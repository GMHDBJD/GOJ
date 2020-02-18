package com.goj.restservice.projection;

import java.time.LocalDate;

public interface ContestSummary {
    Long getContestId();

    String getTitle();

    LocalDate getStartTime();

    LocalDate getEndTime();
}