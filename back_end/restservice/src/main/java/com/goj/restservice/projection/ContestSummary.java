package com.goj.restservice.projection;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.beans.factory.annotation.Value;

public interface ContestSummary {
    Long getContestId();

    String getTitle();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime getStartTime();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime getEndTime();

    @Value("#{target.requirePassword==1}")
    Boolean getRequirePassword();

    @Value("#{target.joined==1}")
    Boolean getJoined();
}