package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import java.io.Serializable;

@Embeddable
class ContestProblemKey implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "problem_id")
    Long problem_id;

    @Column(name = "contest_id")
    Long contest_id;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}

@Entity
public class ContestProblem {

    @EmbeddedId
    ContestProblemKey contest_problem_key;

    @ManyToOne
    @MapsId("problem_id")
    @JoinColumn(name = "problem_id")
    Problem problem;

    @ManyToOne
    @MapsId("contest_id")
    @JoinColumn(name = "contest_id")
    Contest contest;

    @Column(length = 255)
    private String title;

    private long accepted;

    private long submit;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

}