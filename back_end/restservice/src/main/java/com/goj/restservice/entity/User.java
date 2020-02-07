package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @Column(length = 48)
    private String user_id;

    @Column(length = 20)
    private String nickname;

    @Column(length = 100)
    private String email;

    private long submit;

    private long solved;

    @Column(length = 32)
    private String password;

    private Timestamp register_time;

    private Timestamp access_time;

    @Column(length = 46)
    private String ip;

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getSubmit() {
        return submit;
    }

    public void setEmail(long submit) {
        this.submit = submit;
    }

    public long getSolved() {
        return solved;
    }

    public void setSolved(long solved) {
        this.solved = solved;
    }

    public Timestamp getAccessTime() {
        return access_time;
    }

    public void setAccessTime(Timestamp access_time) {
        this.access_time = access_time;
    }

    public Timestamp getRegisterTime() {
        return register_time;
    }

    public void setRegisterTime(Timestamp register_time) {
        this.register_time = register_time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}