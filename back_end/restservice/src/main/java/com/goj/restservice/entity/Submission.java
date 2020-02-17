package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class Submission {
    @Id
    @Column(unique = true, nullable = false)
    private Long submissionId;

    @OneToOne
    @JoinColumn(name = "submissionId", updatable = false, insertable = false, referencedColumnName = "submissionId")
    private SourceCode sourceCode;

    @Column(nullable = false)
    private Long problemId;

    @ManyToOne
    @JoinColumn(name = "problemId", updatable = false, insertable = false, referencedColumnName = "problemId")
    private Problem problem;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, insertable = false, referencedColumnName = "userId")
    private User user;

    private Long time;

    private Long memory;

    private short result;

    @Column(nullable = false)
    private short language;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime submitDateTime;

    @Column(length = 46)
    private String ip;

    private Long contestId;

    @ManyToOne
    @JoinColumn(name = "contestId", updatable = false, insertable = false, referencedColumnName = "contestId")
    private Contest contest;

    private Long codeLength;

    private LocalDateTime judgeTime;

    public Submission(Long submissionId, Long problemId, Short language, Long contestId) {
        this.submissionId = submissionId;
        this.problemId = problemId;
        this.language = language;
        this.contestId = contestId;
    }

}