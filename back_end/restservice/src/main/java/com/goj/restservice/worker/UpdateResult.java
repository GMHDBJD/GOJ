package com.goj.restservice.worker;

import com.goj.restservice.entity.ResultRedis;
import com.goj.restservice.repository.SubmissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UpdateResult {

    @Autowired
    private SubmissionRepository submissionRepository;

    void update(ResultRedis resultRedis) {
        submissionRepository.updateResult(resultRedis.getSubmissionId(), (short) 1, resultRedis.getMemory(),
                resultRedis.getTime());
    }
}