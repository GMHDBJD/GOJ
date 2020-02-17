package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestProblemForm implements Serializable {
    private static final long serialVersionUID = 7892316271416949289L;

    @Min(value = 1, message = "problemId must be greater than or equal to 1")
    @NotNull(message = "problemId could not be null")
    Long problemId;
}