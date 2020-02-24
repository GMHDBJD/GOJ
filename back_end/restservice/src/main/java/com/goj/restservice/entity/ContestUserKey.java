package com.goj.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ContestUserKey implements Serializable {
    private static final long serialVersionUID = 1L;

    Long contestId;

    Long userId;
}