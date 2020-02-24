package com.goj.restservice.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ProblemSummary {
    Long getProblemId();

    String getTitle();

    Long getAccepted();

    Long getSubmit();

    @Value("#{target.submit==0 ? 0 : target.accepted / target.submit}")
    Long getRatio();

    @Value("#{target.solved==1}")
    Boolean getSolved();
}