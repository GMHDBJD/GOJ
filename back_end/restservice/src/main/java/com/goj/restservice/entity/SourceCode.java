package com.goj.restservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class SourceCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long submission_id;

    private String code;

}