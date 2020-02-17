package com.goj.restservice.projection;

public interface ProblemSummary {
    Long getProblemId();

    String getTitle();

    Long getAccepted();

    Long getSubmit();

    Integer getRatio();
}