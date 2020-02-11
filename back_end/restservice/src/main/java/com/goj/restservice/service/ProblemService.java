package com.goj.restservice.service;

import java.util.Optional;

import com.goj.restservice.entity.Problem;
import com.goj.restservice.projection.ProblemProjection;
import com.goj.restservice.repository.ProblemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    public Problem create(Problem new_problem) {
        return problemRepository.save(new_problem);
    }

    public Iterable<ProblemProjection> readAll(int page, int per_page) {
        Page<ProblemProjection> pageResult = problemRepository
                .findAllProblemProjectionBy(PageRequest.of(page, per_page));
        return pageResult.getContent();
    }

    public Problem readOne(Long problemId) {
        Optional<Problem> result = problemRepository.findById(problemId);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public void update(Problem new_problem) {
        problemRepository.save(new_problem);
    }

    public void delete(Long problemId) {
        problemRepository.deleteById(problemId);
    }
}