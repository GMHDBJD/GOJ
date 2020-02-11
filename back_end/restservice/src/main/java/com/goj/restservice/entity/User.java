package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

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

    private Long submit;

    private Long accepted;

    private int ratio;

    @Column(length = 32)
    private String password;

    private LocalDateTime registerTime;

    private LocalDateTime accessTime;

    @Column(length = 46)
    private String ip;

}