package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.projection.ContestProjection;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ContestRepository extends PagingAndSortingRepository<Contest, Long> {
    Page<ContestProjection> findAllContestProjectionBy(Pageable pageable);
}