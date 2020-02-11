package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contestId;

    @Column(length = 255, nullable = false)
    private String title;

    private String description;

    @NotNull(message = "startTime could not be null")
    private LocalDateTime startTime;

    @Future(message = "endTime must be a future date")
    @NotNull(message = "endTime could not be null")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 32)
    private String password;

    @AssertTrue(message = "endTime should be after than startTime")
    public boolean isValid() {
        if (startTime == null || endTime == null)
            return true;
        return endTime.isAfter(startTime);
    }
}