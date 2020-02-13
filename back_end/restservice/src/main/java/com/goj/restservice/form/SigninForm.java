package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigninForm implements Serializable {
    private static final long serialVersionUID = -6986746375915710855L;
    private String username;
    private String password;
}