package com.goj.restservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contestId;

    @Column(length = 255, nullable = false)
    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contest")
    private List<ContestProblem> contestProblem;

    @ManyToOne
    @NotNull
    private User createUser;

    @Column(length = 100)
    private String password;

    public Contest(String title, String description, LocalDateTime startTime, LocalDateTime endTime, String password) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.password = password;
    }
}