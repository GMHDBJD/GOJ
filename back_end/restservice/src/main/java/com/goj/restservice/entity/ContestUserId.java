package com.goj.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContestUserId implements Serializable {

    private static final long serialVersionUID = 7453238872112055257L;

    Long contestId;

    Long userId;
}