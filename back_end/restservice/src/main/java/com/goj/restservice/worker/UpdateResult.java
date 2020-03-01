package com.goj.restservice.worker;

import java.time.LocalDateTime;

import com.goj.restservice.entity.ResultRedis;
import com.goj.restservice.repository.ContestProblemRepository;
import com.goj.restservice.repository.ContestUserRepository;
import com.goj.restservice.repository.ProblemRepository;
import com.goj.restservice.repository.SubmissionRepository;
import com.goj.restservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UpdateResult {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContestProblemRepository contestProblemRepository;

    @Autowired
    private ContestUserRepository contestUserRepository;

    void update(ResultRedis resultRedis) {
        if (resultRedis.getResult() == 5) {
            if (resultRedis.getContestId() != null) {
                contestUserRepository.updateContestUserAccepted(resultRedis.getUserId());
                contestUserRepository.updateContestUserSolve(resultRedis.getUserId(), resultRedis.getProblemId(),
                        resultRedis.getContestId());
                contestProblemRepository.updateContestProblemAccepted(resultRedis.getProblemId(),
                        resultRedis.getContestId());
            }
            userRepository.updateUserAccepted(resultRedis.getUserId());
            userRepository.updateUserSolve(resultRedis.getUserId(), resultRedis.getProblemId());
            problemRepository.updateProblemAccepted(resultRedis.getProblemId());

        }
        submissionRepository.updateResult(resultRedis.getSubmissionId(), resultRedis.getResult(),
                resultRedis.getMemory(), resultRedis.getTime(), resultRedis.getCodeLength(), LocalDateTime.now());

    }
}