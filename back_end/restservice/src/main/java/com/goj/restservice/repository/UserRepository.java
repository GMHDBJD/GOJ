package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.goj.restservice.entity.User;
import com.goj.restservice.projection.UserDetail;
import com.goj.restservice.projection.UserSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT username, nickname, submit, accepted, solved, @curRank\\:=@curRank + 1 AS rank FROM user u, (SELECT @curRank\\:=0) r ORDER BY solved DESC")
    Page<UserSummary> findAllUserSummaryBy(Pageable pageable);

    Optional<User> findByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT username, nickname, email, register_time as registerTime, submit, accepted, solved, @curRank\\:=@curRank + 1 AS rank FROM user u, (SELECT @curRank\\:=0) r WHERE username = ?1 ORDER BY accepted DESC")
    Optional<UserDetail> findUserDetailByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user u SET u.accepted = u.accepted+1 WHERE u.user_id = :userId")
    void updateUserAccepted(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user u SET u.solved = u.solved + 1 WHERE u.user_id = :userId AND NOT EXISTS(SELECT 1 FROM submission s WHERE s.user_id = :userId AND s.contest_id IS NULL AND s.result = 5 AND s.problem_id = :problemId)")
    void updateUserSolve(@Param("userId") Long userId,@Param("problemId") Long problemId);
}