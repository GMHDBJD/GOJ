package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.sql.Timestamp;

@Entity
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long contest_id;

    @Column(length = 255)
    private String title;

    private String description;

    private Timestamp start_time;

    private Timestamp end_time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 32)
    private String password;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return start_time;
    }

    public void setStartTime(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEndTime() {
        return end_time;
    }

    public void setEndTime(Timestamp end_time) {
        this.end_time = end_time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}