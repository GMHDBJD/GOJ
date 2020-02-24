package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class ContestUser {
    @EmbeddedId
    ContestUserKey contestUserKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contestId")
    @JoinColumn(name = "contest_id", nullable = false)
    Contest contest;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false)
    private Long accepted = 0L;

    @Column(nullable = false)
    private Long submit = 0L;

    @Column(nullable = false)
    private Long solved = 0L;

    public ContestUser(ContestUserKey contestUserKey, Contest contest, User user) {
        this.contestUserKey = contestUserKey;
        this.contest = contest;
        this.user = user;
    }
}