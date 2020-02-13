package com.goj.restservice.projection;

import java.time.LocalDateTime;
import java.util.List;

public interface UserSummary {

    String getUsername();

    String getNickname();

    List<String> getRoles();

    LocalDateTime getRegisterTime();

    LocalDateTime getAccessTime();

    String getIp();

    Long getSubmit();

    Long getAccepted();

    Integer getRatio();
}