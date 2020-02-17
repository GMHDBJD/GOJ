package com.goj.restservice.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 100)
    private String source;

    private String description;

    private String input;

    private String output;

    private String sampleInput;

    private String sampleOutput;

    private String hint;

    @Column(nullable = false)
    private LocalDate createDate;

    private Long timeLimit;

    private Long memoryLimit;

    @Column(nullable = false)
    private Long accepted = 0L;

    @Column(nullable = false)
    private Long submit = 0L;

    @Column(nullable = false)
    private Integer ratio = 0;

    @Column(nullable = false)
    private Boolean randomTest = false;

    @PrePersist
    protected void prePersist() {
        if (createDate == null)
            createDate = LocalDate.now();
    }

    public Problem(String title, String source, String description, String input, String output, String sampleInput,
            String sampleOutput, String hint, Long timeLimit, Long memoryLimit) {
        this.title = title;
        this.source = source;
        this.description = description;
        this.input = input;
        this.output = output;
        this.sampleInput = sampleInput;
        this.sampleOutput = sampleOutput;
        this.hint = hint;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }
}