package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.goj.restservice.entity.ContestUser;
import com.goj.restservice.entity.ContestUserId;
import com.goj.restservice.projection.UserSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface ContestUserRepository extends PagingAndSortingRepository<ContestUser, ContestUserId> {
    public Page<UserSummary> findAllByContestId(Long contestId, Pageable pageable);

    boolean existsByContestIdAndUserId(Long contestId, Long userId);
}