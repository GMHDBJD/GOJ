package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@IdClass(ContestUserId.class)
public class ContestUser {
    @Id
    @NotNull(message = "userId could not be null")
    private Long userId;

    @Id
    @NotNull(message = "contestId could not be null")
    private Long contestId;

    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, insertable = false, referencedColumnName = "userId")
    User user;

    @ManyToOne
    @JoinColumn(name = "contestId", updatable = false, insertable = false, referencedColumnName = "contestId")
    Contest contest;

    @Column(nullable = false)
    private Long accepted = 0L;

    @Column(nullable = false)
    private Long submit = 0L;

    @Column(nullable = false)
    private Long solved = 0L;

    public ContestUser(Long contestId, Long userId) {
        this.userId = userId;
        this.contestId = contestId;
    }

}