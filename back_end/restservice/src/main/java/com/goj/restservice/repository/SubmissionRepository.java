package com.goj.restservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.goj.restservice.entity.Submission;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SubmissionRepository extends PagingAndSortingRepository<Submission, Integer> {

}