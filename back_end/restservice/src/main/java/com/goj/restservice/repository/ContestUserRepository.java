package com.goj.restservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.goj.restservice.entity.ContestUser;
import com.goj.restservice.entity.ContestUserId;
import com.goj.restservice.projection.UserSummary;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface ContestUserRepository extends PagingAndSortingRepository<ContestUser, ContestUserId> {

    @Query(nativeQuery = true, value = "SELECT u.username, u.nickname, cu.submit, cu.accepted, cu.solved, @curRank\\:=@curRank + 1 AS rank FROM user u, contest_user cu, (SELECT @curRank\\:=0) r WHERE cu.contest_id = :contestId AND u.user_id = cu.user_id ORDER BY solved DESC")
    public Page<UserSummary> findAllByContestId(@Param("contestId") Long contestId, Pageable pageable);

    boolean existsByContestIdAndUserId(Long contestId, Long userId);
}