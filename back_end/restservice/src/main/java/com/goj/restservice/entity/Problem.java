package com.goj.restservice.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(nullable = false)
    private LocalDate createDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDate lastModifiedDate;

    @CreatedBy
    private String createUser;

    @LastModifiedBy
    private String lastModifiedUser;

    private Long timeLimit;

    private Long memoryLimit;

    @Column(nullable = false)
    private Long accepted = 0L;

    @Column(nullable = false)
    private Long submit = 0L;

    @Column(nullable = false)
    private Boolean randomTest = false;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContestProblem> contestProblemSet;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Submission> submissionSet;

    public Problem(String title, String source, String description, String input, String output, String sampleInput,
            String sampleOutput, String hint, Long timeLimit, Long memoryLimit) {
        this.update(title, source, description, input, output, sampleInput, sampleOutput, hint, timeLimit, memoryLimit);
    }

    public void update(String title, String source, String description, String input, String output, String sampleInput,
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
        this.createDate = LocalDate.now();
    }
}