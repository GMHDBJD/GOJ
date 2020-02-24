package com.goj.restservice.projection;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

public interface ProblemDetail {
    Long getProblemId();

    String getTitle();

    String getSource();

    String getDescription();

    String getInput();

    String getOutput();

    String getSampleInput();

    String getSampleOutput();

    String getHint();

    LocalDate getCreateDate();

    Long getTimeLimit();

    Long getMemoryLimit();

    Long getSubmit();

    Long getAccepted();

    @Value("#{target.submit==0 ? 0 : target.accepted / target.submit}")
    Long getRatio();
}