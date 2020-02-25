package com.goj.restservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submissionId;

    @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL)
    private SourceCode sourceCode;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    Problem problem;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = true)
    Contest contest;

    private Long time;

    private Long memory;

    private short result;

    @Column(nullable = false)
    private short language;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime submitDateTime;

    @Column(length = 46)
    private String ip;

    private Long codeLength;

    private LocalDateTime judgeTime;

    public Submission(Problem problem, User user, Short language) {
        this.problem = problem;
        this.user = user;
        this.language = language;
    }
}