package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionForm implements Serializable {

    private static final long serialVersionUID = 1007114340746696309L;

    @NotNull(message = "code could not be null.")
    @Size(max = 65535, message = "code to long")
    String code;

    @NotNull(message = "problemId could not be null.")
    Long problemId;

    @Max(value = 4, message = "language should be less than or equal to 4")
    @Min(value = 0, message = "language should be greater than or equal to 0")
    @NotNull(message = "language could not be null.")
    Short language;

    Long contestId;
}