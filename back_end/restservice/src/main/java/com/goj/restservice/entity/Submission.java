package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import java.sql.Timestamp;

@Entity
public class Submission {
    @Id
    @Column(unique = true, nullable = false)
    private long submission_id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "submission_id")
    private SourceCode source_code;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private long time;

    private long memory;

    private short result;

    private short language;

    private Timestamp in_date;

    @Column(length = 46)
    private String ip;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private long code_length;

    private Timestamp judge_time;

    public long getProblemId() {
        return submission_id;
    }

    public void setProblemId(long problem_id) {
        this.submission_id = problem_id;
    }

    public Timestamp getInDate() {
        return in_date;
    }

    public void setInDate(Timestamp in_date) {
        this.in_date = in_date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    public short getResult() {
        return result;
    }

    public void setResult(short result) {
        this.result = result;
    }

    public short getLanguage() {
        return language;
    }

    public void setLanguage(short language) {
        this.language = language;
    }

    public long getCodeLength() {
        return code_length;
    }

    public void setCodeLength(long code_length) {
        this.code_length = code_length;
    }

    public Timestamp getJudgeTime() {
        return judge_time;
    }

    public void setJudgeTime(Timestamp judge_time) {
        this.judge_time = judge_time;
    }
}