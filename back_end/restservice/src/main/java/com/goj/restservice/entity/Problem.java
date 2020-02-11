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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    private LocalDate createDate;

    private Long timeLimit;

    private Long memoryLimit;

    private Long accepted;

    private Long submit;

    private int ratio;

    private Boolean randomTest;

    @PrePersist
    protected void prePersist() {
        if (createDate == null)
            createDate = LocalDate.now();
    }

}