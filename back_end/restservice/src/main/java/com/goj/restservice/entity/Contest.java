package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contestId;

    @Column(length = 255, nullable = false)
    @NotNull(message = "title could not be null")
    private String title;

    private String description;

    @NotNull(message = "startTime could not be null")
    private LocalDateTime startTime;

    @Future(message = "endTime must be a future date")
    @NotNull(message = "endTime could not be null")
    private LocalDateTime endTime;

    @ManyToMany
    @JoinTable(name = "contest_user", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @ManyToOne
    private User createUser;

    @Column(length = 32)
    private String password;

    @AssertTrue(message = "endTime should be after than startTime")
    public boolean isValid() {
        if (startTime == null || endTime == null)
            return false;
        return endTime.isAfter(startTime);
    }
}