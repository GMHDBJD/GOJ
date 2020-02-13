package com.goj.restservice.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    @Column(length = 200, nullable = false)
    @NotNull(message = "title could not be null")
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

}