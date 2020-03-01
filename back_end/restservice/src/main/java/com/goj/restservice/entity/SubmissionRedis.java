package com.goj.restservice.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SubmissionRedis implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long submissionId;

    private String code;

    Long problemId;

    private String language;

    Long timeLimit;

    Long memoryLimit;

    Long userId;

    Long contestId;
}