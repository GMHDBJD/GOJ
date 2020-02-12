package com.goj.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContestProblemId implements Serializable {
    private static final long serialVersionUID = 1L;

    Long contestId;

    Long problemId;
}