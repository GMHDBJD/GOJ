package com.goj.restservice.projection;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface SubmissionSummary {
    Long getSubmissionId();

    @JsonProperty("problemId")
    Long getProblemProblemId();

    @JsonProperty("username")
    String getUserUsername();

    Long getTime();

    Long getMemory();

    short getResult();

    short getLanguage();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime getSubmitDateTime();

    Long getCodeLength();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime getJudgeTime();
}