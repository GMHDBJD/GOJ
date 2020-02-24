package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.ContestSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ContestRepository extends PagingAndSortingRepository<Contest, Long> {

    @Query(nativeQuery = true, value = "SELECT contest_id AS contestId, title, start_time AS startTime, end_time AS endTime, password IS NOT NULL AS requirePassword, (SELECT EXISTS(SELECT 1 FROM contest_user cu WHERE c.contest_id = cu.contest_id AND cu.user_Id = :userId)) AS joined FROM contest c ORDER BY contest_id", countQuery = "SELECT COUNT(1) FROM contest")
    Page<ContestSummary> findAllContestSummaryByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT contest_id AS contestId, title, start_time AS startTime, end_time AS endTime, password IS NOT NULL AS requirePassword, 0 AS joined FROM contest c ORDER BY contest_id", countQuery = "SELECT COUNT(1) FROM contest")
    Page<ContestSummary> findAllContestSummaryBy(Pageable pageable);

    Page<ContestDetail> findAllContestDetailBy(Pageable pageable);

    Optional<ContestDetail> findByContestId(Long contestId);
}