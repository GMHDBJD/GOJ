package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm implements Serializable {
    private static final long serialVersionUID = -6986746375915710855L;

    @Size(max = 15, min = 1, message = "username length should between 1 and 15")
    @NotBlank(message = "username could not be blank")
    private String username;

    @Size(max = 15, min = 5, message = "password length should between 5 and 15")
    @NotNull(message = "password could not be null")
    private String password;

    private String confirmPassword;

    @Size(max = 15, message = "nickname length must be less than or equal to 15")
    private String nickname;

    @Email(message = "invalid email")
    @NotNull(message = "email could not be null")
    private String email;

    @AssertTrue(message = "inconsistent password")
    public boolean isValid() {
        if (password == null || confirmPassword == null)
            return true;
        return password.equals(confirmPassword);
    }
}