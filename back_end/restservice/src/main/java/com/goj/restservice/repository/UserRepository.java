package com.goj.restservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

import com.goj.restservice.entity.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}