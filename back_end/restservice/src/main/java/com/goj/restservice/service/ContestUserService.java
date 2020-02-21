package com.goj.restservice.service;

import com.goj.restservice.entity.ContestUser;
import com.goj.restservice.entity.ContestUserId;
import com.goj.restservice.projection.UserSummary;
import com.goj.restservice.repository.ContestUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ContestUserService {
    @Autowired
    private ContestUserRepository contestUserRepository;

    public ContestUser create(ContestUser newContestUser) {
        return contestUserRepository.save(newContestUser);
    }

    public Iterable<UserSummary> readAll(Long contestId, int page, int per_page) {
        return contestUserRepository.findAllByContestId(contestId, PageRequest.of(page, per_page));
    }

    public void update(ContestUser newContestUser) {
        contestUserRepository.save(newContestUser);
    }

    public void delete(ContestUserId contestUserId) {
        contestUserRepository.deleteById(contestUserId);
    }

    public boolean existsByContestIdUserId(Long contestId, Long userId) {
        return contestUserRepository.existsByContestIdAndUserId(contestId, userId);
    }
}