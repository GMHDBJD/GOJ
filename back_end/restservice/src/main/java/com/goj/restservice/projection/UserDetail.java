package com.goj.restservice.projection;

import java.time.LocalDateTime;
import java.util.List;

public interface UserDetail {

    Long getUserId();

    String getUsername();

    String getNickname();

    String getEmail();

    List<String> getRoles();

    LocalDateTime getRegisterTime();

    LocalDateTime getAccessTime();

    String getIp();

    Long getSubmit();

    Long getAccepted();

    Integer getRatio();
}