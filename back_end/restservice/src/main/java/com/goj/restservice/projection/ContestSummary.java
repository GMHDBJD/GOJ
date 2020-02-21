package com.goj.restservice.projection;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

public interface ContestSummary {
    Long getContestId();

    String getTitle();

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();

    @Value("#{target.password!=null}")
    String getRequirePassword();
}