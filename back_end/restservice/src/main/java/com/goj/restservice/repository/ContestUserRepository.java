package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.goj.restservice.entity.ContestUser;
import com.goj.restservice.entity.ContestUserKey;
import com.goj.restservice.projection.UserSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface ContestUserRepository extends PagingAndSortingRepository<ContestUser, ContestUserKey> {

    @Query(nativeQuery = true, value = "SELECT u.username, u.nickname, cu.submit, cu.accepted, cu.solved, @curRank\\:=@curRank + 1 AS rank FROM user u, contest_user cu, (SELECT @curRank\\:=0) r WHERE cu.contest_id = :contestId AND u.user_id = cu.user_id ORDER BY solved DESC")
    public Page<UserSummary> findAllUserSummaryByContestId(@Param("contestId") Long contestId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE contest_user cu SET cu.accepted = cu.accepted+1 where cu.user_id = :userId")
    void updateContestUserAccepted(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE contest_user cu SET cu.solved = cu.solved + 1 where cu.user_id = :userId and NOT EXISTS(SELECT 1 FROM submission s WHERE s.user_id = :userId AND s.contest_id = :contestId AND s.result = 5 AND s.problem_id = :problemId)")
    void updateContestUserSolve(@Param("userId") Long userId, @Param("problemId") Long problemId,
            @Param("contestId") Long contestId);
}