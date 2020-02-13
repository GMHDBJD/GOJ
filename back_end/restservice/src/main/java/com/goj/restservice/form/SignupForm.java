package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm implements Serializable {
    private static final long serialVersionUID = -6986746375915710855L;

    @Size(max = 15, min = 1, message = "username length should between 1 and 15")
    @NotNull(message = "username could not be null")
    private String username;

    @Size(max = 15, min = 5, message = "password length should between 5 and 15")
    @NotNull(message = "password could not be null")
    private String password;

    private String confirmPassword;

    @Size(max = 15, message = "username length must be less than or equal to 1")
    private String nickname;

    @Email(message = "invalid message")
    @NotNull(message = "email could not be null")
    private String email;

    @AssertTrue(message = "inconsistent password")
    public boolean isValid() {
        return password.equals(confirmPassword);
    }
}