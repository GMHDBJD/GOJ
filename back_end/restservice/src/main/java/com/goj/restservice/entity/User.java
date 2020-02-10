package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@RequiredArgsConstructor
public class User {
    @Id
    @Column(length = 48)
    private String userId;

    @Column(length = 20)
    private String nickname;

    @Column(length = 100)
    private String email;

    private long submit;

    private long accepted;

    private int ratio;

    @Column(length = 32)
    private String password;

    private Timestamp registerTime;

    private Timestamp accessTime;

    @Column(length = 46)
    private String ip;

}