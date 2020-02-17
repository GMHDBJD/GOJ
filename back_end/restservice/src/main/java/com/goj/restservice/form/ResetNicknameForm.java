package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetNicknameForm implements Serializable {
    private static final long serialVersionUID = 3092570421620235020L;

    @Size(max = 15, message = "nickname length must be less than or equal to 15")
    String newNickname;
}