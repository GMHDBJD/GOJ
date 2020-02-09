package com.goj.restservice.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long problem_id;

    @Column(length = 200, nullable = false)
    @NotNull(message = "title could not be null")
    private String title;

    @Column(length = 100)
    private String source;

    private String description;

    private String input;

    private String output;

    private String sample_input;

    private String sample_output;

    private String hint;

    @Temporal(TemporalType.DATE)
    private Date create_date;

    private long time_limit;

    private long memory_limit;

    private long accepted;

    private long submit;

    private Boolean random_test;

    @PrePersist
    protected void prePersist() {
        if (create_date == null)
            create_date = new Date();
    }

}