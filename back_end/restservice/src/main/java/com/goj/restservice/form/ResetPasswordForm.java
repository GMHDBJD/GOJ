package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordForm implements Serializable {
    private static final long serialVersionUID = -6986746375915710855L;

    private String oldPassword;

    @Size(max = 15, min = 5, message = "password length should between 5 and 15")
    @NotNull(message = "password could not be null")
    private String newPassword;

    private String confirmPassword;

    @AssertTrue(message = "inconsistent password")
    public boolean isValid() {
        return newPassword.equals(confirmPassword);
    }
}