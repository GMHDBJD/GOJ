package com.goj.restservice.entity;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contestId;

    @Column(length = 255, nullable = false)
    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

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

    @Column(length = 100)
    private String password;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContestProblem> contestProblemSet;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContestUser> contestUserSet;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Submission> contestSet;

    public Contest(String title, String description, LocalDateTime startTime, LocalDateTime endTime, String password) {
        this.update(title, description, startTime, endTime, password);
    }

    public void update(String title, String description, LocalDateTime startTime, LocalDateTime endTime,
            String password) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.password = password;
    }
}