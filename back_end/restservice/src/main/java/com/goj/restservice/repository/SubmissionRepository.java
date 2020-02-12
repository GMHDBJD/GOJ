package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

import com.goj.restservice.entity.Submission;
import com.goj.restservice.projection.SubmissionDetail;
import com.goj.restservice.projection.SubmissionSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SubmissionRepository extends PagingAndSortingRepository<Submission, Long> {
    Page<SubmissionSummary> findAllSubmissionSummaryBy(Pageable pageable);

    Optional<SubmissionDetail> findSubmissionDetailBySubmissionId(Long submissionId);
}