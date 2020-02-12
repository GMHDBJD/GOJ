package com.goj.restservice.projection;

import java.time.LocalDateTime;

public interface SubmissionSummary {
    Long getSubmissionId();

    Long getProblemProblemId();

    String getUserUserId();

    Long getTime();

    Long getMemory();

    short getResult();

    short getLanguage();

    LocalDateTime getSubmitDateTime();

    Long getCodeLength();

    LocalDateTime getJudgeTime();
}