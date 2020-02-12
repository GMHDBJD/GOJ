package com.goj.restservice.service;

import java.util.Optional;

import com.goj.restservice.entity.Submission;
import com.goj.restservice.projection.SubmissionDetail;
import com.goj.restservice.projection.SubmissionSummary;
import com.goj.restservice.repository.SubmissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    public Submission create(Submission newSubmission) {
        return submissionRepository.save(newSubmission);
    }

    public Iterable<SubmissionSummary> readAll(int page, int per_page) {
        Page<SubmissionSummary> pageResult = submissionRepository
                .findAllSubmissionSummaryBy(PageRequest.of(page, per_page));
        return pageResult.getContent();
    }

    public SubmissionDetail readOne(Long submissionId) {
        Optional<SubmissionDetail> result = submissionRepository.findSubmissionDetailBySubmissionId(submissionId);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }
}