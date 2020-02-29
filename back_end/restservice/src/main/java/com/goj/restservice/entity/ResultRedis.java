package com.goj.restservice.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ResultRedis implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long submissionId;

    Long time;

    Long memory;

    Long codeLength;

    Short result;
}