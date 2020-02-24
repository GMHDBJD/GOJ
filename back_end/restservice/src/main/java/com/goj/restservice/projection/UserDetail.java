package com.goj.restservice.projection;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

public interface UserDetail {

    String getUsername();

    String getNickname();

    String getEmail();

    LocalDateTime getRegisterTime();

    Long getSubmit();

    Long getAccepted();

    @Value("#{target.submit==0 ? 0 : target.accepted / target.submit}")
    Long getRatio();

    Long getSolved();

    Long getRank();
}