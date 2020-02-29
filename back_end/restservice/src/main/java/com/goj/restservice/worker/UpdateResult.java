package com.goj.restservice.worker;

import java.time.LocalDateTime;

import com.goj.restservice.entity.ResultRedis;
import com.goj.restservice.repository.SubmissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UpdateResult {

    @Autowired
    private SubmissionRepository submissionRepository;

    void update(ResultRedis resultRedis) {
        submissionRepository.updateResult(resultRedis.getSubmissionId(), resultRedis.getResult(),
                resultRedis.getMemory(), resultRedis.getTime(), resultRedis.getCodeLength(), LocalDateTime.now());
    }
}