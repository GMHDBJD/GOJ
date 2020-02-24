package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestProblemForm implements Serializable {
    private static final long serialVersionUID = 7892316271416949289L;

    @Min(value = 1, message = "problemId must be greater than or equal to 1")
    @NotNull(message = "problemId could not be null")
    Long problemId;

    @NotNull(message = "title could not be null")
    @Size(max = 255, message = "title length length should less than or equal to 255")
    private String title;
}