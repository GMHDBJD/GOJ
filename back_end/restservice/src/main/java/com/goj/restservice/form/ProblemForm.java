package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemForm implements Serializable {

    private static final long serialVersionUID = 7780755677475027070L;

    @NotNull(message = "title could not be null")
    @Size(max = 255, message = "title length length should less than or equal to 255")
    private String title;

    @Column(length = 100)
    private String source;

    private String description;

    private String input;

    private String output;

    private String sampleInput;

    private String sampleOutput;

    private String hint;

    private Long timeLimit;

    private Long memoryLimit;
}