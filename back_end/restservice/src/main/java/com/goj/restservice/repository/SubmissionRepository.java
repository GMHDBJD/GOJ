package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.entity.Submission;
import com.goj.restservice.projection.SubmissionDetail;
import com.goj.restservice.projection.SubmissionSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SubmissionRepository extends PagingAndSortingRepository<Submission, Long> {
    Page<SubmissionSummary> findAllSubmissionSummaryBy(Pageable pageable);

    Page<SubmissionSummary> findAllSubmissionSummaryByContest(Contest contest, Pageable pageable);

    Optional<SubmissionDetail> findSubmissionDetailBySubmissionId(Long submissionId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update submission s set s.result = :result, s.time = :time, s.memory = :memory WHERE s.submission_id = :submissionId")
    void updateResult(@Param("submissionId") Long submissionId, @Param("result") Short result,
            @Param("memory") Long memory, @Param("time") Long time);
}