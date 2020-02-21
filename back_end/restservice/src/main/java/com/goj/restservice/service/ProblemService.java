package com.goj.restservice.service;

import java.util.Optional;

import com.goj.restservice.entity.Problem;
import com.goj.restservice.projection.ProblemSummary;
import com.goj.restservice.repository.ProblemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    public Problem create(Problem newProblem) {
        return problemRepository.save(newProblem);
    }

    public Iterable<ProblemSummary> readAll(int page, int per_page) {
        return problemRepository.findAllProblemSummaryBy(PageRequest.of(page, per_page));
    }

    public Problem readOne(Long problemId) {
        Optional<Problem> result = problemRepository.findById(problemId);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public void update(Problem newProblem) {
        problemRepository.save(newProblem);
    }

    public void delete(Long problemId) {
        problemRepository.deleteById(problemId);
    }
}