package com.goj.restservice.projection;

import org.springframework.beans.factory.annotation.Value;

public interface UserSummary {

    String getUsername();

    String getNickname();

    Long getSubmit();

    Long getAccepted();

    @Value("#{target.submit==0 ? 0 : target.accepted / target.submit}")
    Long getRatio();

    Long getSolved();

    Long getRank();
}