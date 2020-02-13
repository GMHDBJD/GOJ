package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

import com.goj.restservice.entity.User;
import com.goj.restservice.projection.UserDetail;
import com.goj.restservice.projection.UserSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<UserDetail> findUserDetailByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<UserSummary> findAllUserSummaryBy(Pageable pageable);

    void deleteByUsername(String username);
}