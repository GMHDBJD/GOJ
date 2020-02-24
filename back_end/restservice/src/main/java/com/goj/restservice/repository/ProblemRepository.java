package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.goj.restservice.entity.Problem;
import com.goj.restservice.projection.ProblemSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProblemRepository extends PagingAndSortingRepository<Problem, Long> {

    @Query(nativeQuery = true, value = "SELECT problem_id as problemId, title, accepted, submit, (SELECT EXISTS(SELECT 1 FROM submission s WHERE s.user_id = :userId AND s.contest_id IS NULL AND s.result = 0 AND p.problem_id = s.problem_id)) as solved FROM problem p ORDER BY p.problem_id", countQuery = "SELECT COUNT(1) FROM problem")
    Page<ProblemSummary> findAllProblemSummaryByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT problem_id as problemId, title, accepted, submit, 0 AS solved FROM problem p")
    Page<ProblemSummary> findAllProblemSummaryBy(Pageable pageable);
}