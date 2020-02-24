package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@IdClass(ContestProblemId.class)
public class ContestProblem {
    @Id
    private Long problemId;

    @Id
    private Long contestId;

    @ManyToOne
    @JoinColumn(name = "problemId", updatable = false, insertable = false, referencedColumnName = "problemId")
    Problem problem;

    @ManyToOne
    @JoinColumn(name = "contestId", updatable = false, insertable = false, referencedColumnName = "contestId")
    Contest contest;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(nullable = false)
    private Long accepted = 0L;

    @Column(nullable = false)
    private Long submit = 0L;

    @Column(nullable = false)
    private Long solved = 0L;

    public ContestProblem(Long contestId, Long problemId, String title) {
        this.contestId = contestId;
        this.problemId = problemId;
        this.title = title;
    }
}