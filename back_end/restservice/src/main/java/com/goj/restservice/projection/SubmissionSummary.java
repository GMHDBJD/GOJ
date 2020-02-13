package com.goj.restservice.projection;

import java.time.LocalDateTime;

public interface SubmissionSummary {
    Long getSubmissionId();

    Long getProblemProblemId();

    String getUserUsername();

    Long getTime();

    Long getMemory();

    short getResult();

    short getLanguage();

    LocalDateTime getSubmitDateTime();

    Long getCodeLength();

    LocalDateTime getJudgeTime();
}