package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@RequiredArgsConstructor
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contest_id;

    @Column(length = 255)
    private String title;

    private String description;

    private Timestamp startTime;

    private Timestamp endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 32)
    private String password;

}