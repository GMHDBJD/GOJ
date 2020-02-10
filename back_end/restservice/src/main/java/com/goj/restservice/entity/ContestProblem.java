package com.goj.restservice.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Embeddable
class ContestProblemKey implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "problem_id")
    Long problemId;

    @Column(name = "contest_id")
    Long contestId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}

@Data
@Entity
@RequiredArgsConstructor
public class ContestProblem {

    @EmbeddedId
    ContestProblemKey contestProblemKey;

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

    private Long accepted;

    private Long submit;

}