package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.goj.restservice.entity.Problem;
import com.goj.restservice.projection.ProblemProjection;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProblemRepository extends PagingAndSortingRepository<Problem, Long> {
    Page<ProblemProjection> findAllProblemProjectionBy(Pageable pageable);
}