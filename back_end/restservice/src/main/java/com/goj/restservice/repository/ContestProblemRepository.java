package com.goj.restservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.goj.restservice.entity.ContestProblem;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ContestProblemRepository extends CrudRepository<ContestProblem, Integer> {

}