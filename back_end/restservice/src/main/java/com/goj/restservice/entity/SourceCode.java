package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class SourceCode {
    @Id
    private Long submissionId;

    @OneToOne
    @MapsId
    @JoinColumn(name="submission_id")
    private Submission submission;

    @Column(nullable = false)
    private String code;

    public SourceCode(Submission submission, String code) {
        this.submission = submission;
        this.code = code;
    }
}