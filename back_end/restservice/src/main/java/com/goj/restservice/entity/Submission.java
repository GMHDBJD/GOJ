package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@RequiredArgsConstructor
public class Submission {
    @Id
    @Column(unique = true, nullable = false)
    private long submissionId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "submission_id")
    private SourceCode sourceCode;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private long time;

    private long memory;

    private short result;

    private short language;

    private Timestamp inDate;

    @Column(length = 46)
    private String ip;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private long codeLength;

    private Timestamp judgeTime;

}