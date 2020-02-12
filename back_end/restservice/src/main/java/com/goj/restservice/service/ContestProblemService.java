package com.goj.restservice.service;

import com.goj.restservice.entity.ContestProblem;
import com.goj.restservice.entity.ContestProblemId;
import com.goj.restservice.projection.ProblemSummary;
import com.goj.restservice.repository.ContestProblemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ContestProblemService {
    @Autowired
    private ContestProblemRepository contestProblemRepository;

    public ContestProblem create(ContestProblem newContestProblem) {
        return contestProblemRepository.save(newContestProblem);
    }

    public Iterable<ProblemSummary> readAll(Long contestId, int page, int per_page) {
        Page<ProblemSummary> pageResult = contestProblemRepository.findAllByContestId(contestId,
                PageRequest.of(page, per_page));
        return pageResult.getContent();
    }

    public void update(ContestProblem newContestProblem) {
        contestProblemRepository.save(newContestProblem);
    }

    public void delete(ContestProblemId contestProblemId) {
        contestProblemRepository.deleteById(contestProblemId);
    }
}