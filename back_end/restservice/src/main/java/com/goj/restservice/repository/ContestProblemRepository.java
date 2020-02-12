package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.goj.restservice.entity.ContestProblem;
import com.goj.restservice.entity.ContestProblemId;
import com.goj.restservice.projection.ProblemSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface ContestProblemRepository extends PagingAndSortingRepository<ContestProblem, ContestProblemId> {
    public Page<ProblemSummary> findAllByContestId(Long contestId, Pageable pageable);
}