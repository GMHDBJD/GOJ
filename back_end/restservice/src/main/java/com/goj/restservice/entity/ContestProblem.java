package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class ContestProblem {
    @EmbeddedId
    ContestProblemKey contestProblemKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contestId")
    @JoinColumn(name = "contest_id", nullable = false)
    Contest contest;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("problemId")
    @JoinColumn(name = "problem_id", nullable = false)
    Problem problem;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(nullable = false)
    private Long accepted = 0L;

    @Column(nullable = false)
    private Long submit = 0L;

    @Column(nullable = false)
    private Long solved = 0L;

    public ContestProblem(ContestProblemKey contestProblemKey, Contest contest, Problem problem, String title) {
        this.contestProblemKey = contestProblemKey;
        this.contest = contest;
        this.problem = problem;
        this.title = title;
    }
}