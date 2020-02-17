package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionForm implements Serializable {

    private static final long serialVersionUID = 1007114340746696309L;

    @NotNull(message = "code could not be null.")
    String code;

    @NotNull(message = "problemId could not be null.")
    Long problemId;

    @Max(value = 10, message = "language should be less than or equal to 10")
    @Min(value = 1, message = "language should be greater than or equal to 1")
    Short Language;

    Long contestId;
}