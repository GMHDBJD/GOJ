package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity // This tells Hibernate to make a table out of this class
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long problem_id;

    @Column(length = 200)
    private String title;

    @Column(length = 100)
    private String source;

    private String description;

    private String input;

    private String output;

    private String sample_input;

    private String sample_output;

    private String hint;

    private Timestamp in_date;

    private long time_limit;

    private long memory_limit;

    private long accepted;

    private long submit;

    private long solved;

    private Boolean random_test;

    @Column(length = 32)
    private String password;

    private Timestamp register_time;

    private Timestamp access_time;

    @Column(length = 46)
    private String ip;

    public long getProblemId() {
        return problem_id;
    }

    public void setProblemId(long problem_id) {
        this.problem_id = problem_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

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

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getSampleInput() {
        return sample_input;
    }

    public void setSampleInput(String sample_input) {
        this.sample_input = sample_input;
    }

    public String getSampleOutput() {
        return sample_output;
    }

    public void setSampleOutput(String sample_output) {
        this.sample_output = sample_output;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Timestamp getInDate() {
        return in_date;
    }

    public void setInDate(Timestamp in_date) {
        this.in_date = in_date;
    }

    public long getTimeLimit() {
        return time_limit;
    }

    public void setTimeLimit(long time_limit) {
        this.time_limit = time_limit;
    }

    public long getMemoryLimit() {
        return memory_limit;
    }

    public void setMemoryLimit(long memory_limit) {
        this.memory_limit = memory_limit;
    }

    public long getAccepted() {
        return accepted;
    }

    public void setAccepted(long accepted) {
        this.accepted = accepted;
    }

    public long getSubmit() {
        return submit;
    }

    public void setSubmit(long submit) {
        this.submit = submit;
    }

    public long getSolved() {
        return solved;
    }

    public void setSolved(long solved) {
        this.solved = solved;
    }

    public Boolean getRandomTest() {
        return random_test;
    }

    public void setRandomTest(Boolean random_test) {
        this.random_test = random_test;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getRegisterTime() {
        return register_time;
    }

    public void setRegisterTime(Timestamp register_time) {
        this.register_time = register_time;
    }

    public Timestamp getAccessTime() {
        return access_time;
    }

    public void setAccessTime(Timestamp access_time) {
        this.access_time = access_time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}