package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

import com.goj.restservice.entity.ContestProblem;
import com.goj.restservice.entity.ContestProblemKey;
import com.goj.restservice.projection.ProblemSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface ContestProblemRepository extends PagingAndSortingRepository<ContestProblem, ContestProblemKey> {

    @Query(nativeQuery = true, value = "SELECT problem_id as problemId, title, accepted, submit, (SELECT EXISTS(SELECT 1 FROM submission s WHERE s.user_id = :userId AND s.contest_id=:contestId AND s.result = 5 AND cp.problem_id = s.problem_id)) as solved FROM contest_problem cp WHERE contest_id=:contestId ORDER BY cp.problem_id", countQuery = "SELECT COUNT(1) FROM contest_problem WHERE contest_id=:contestId")
    public Page<ProblemSummary> findAllProblemSummaryByContestIdAndUserId(@Param("contestId") Long contestId,
            @Param("userId") Long userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update contest_problem cp set cp.accepted = cp.accepted+1 where cp.problem_id = :problemId and cp.contest_id = :contestId")
    void updateContestProblemAccepted(@Param("problemId") Long problemId, @Param("contestId") Long contestId);
}