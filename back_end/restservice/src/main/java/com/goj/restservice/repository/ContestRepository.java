package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.ContestSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ContestRepository extends PagingAndSortingRepository<Contest, Long> {
    Page<ContestSummary> findAllContestSummaryBy(Pageable pageable);

    Page<ContestDetail> findAllContestDetailBy(Pageable pageable);

    Optional<ContestDetail> findByContestId(Long contestId);
}