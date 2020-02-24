package com.goj.restservice.projection;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface SubmissionDetail {
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Long getCodeLength();

    LocalDateTime getJudgeTime();

    @JsonProperty("code")
    String getSourceCodeCode();
}