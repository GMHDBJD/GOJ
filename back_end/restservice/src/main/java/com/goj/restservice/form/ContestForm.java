package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "title could not be null")
    @Size(max = 255, message = "title length length should less than or equal to 255")
    private String title;

    private String description;

    @NotNull(message = "startTime could not be null")
    private LocalDateTime startTime;

    @NotNull(message = "endTime could not be null")
    private LocalDateTime endTime;

    @Size(max = 15, min = 5, message = "password length should between 5 and 15")
    private String password;

    @AssertTrue(message = "endTime should be after than startTime")
    public boolean isValid() {
        if (startTime == null || endTime == null)
            return true;
        return endTime.isAfter(startTime);
    }
}