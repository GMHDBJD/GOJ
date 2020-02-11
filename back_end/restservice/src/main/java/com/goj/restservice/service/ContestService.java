package com.goj.restservice.service;

import java.util.Optional;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.projection.ContestProjection;
import com.goj.restservice.repository.ContestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ContestService {
    @Autowired
    private ContestRepository contestRepository;

    public Contest create(Contest new_contest) {
        return contestRepository.save(new_contest);
    }

    public Iterable<ContestProjection> readAll(int page, int per_page) {
        Page<ContestProjection> pageResult = contestRepository
                .findAllContestProjectionBy(PageRequest.of(page, per_page));
        return pageResult.getContent();
    }

    public Contest readOne(Long contestId) {
        Optional<Contest> result = contestRepository.findById(contestId);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public void update(Contest new_contest) {
        contestRepository.save(new_contest);
    }

    public void delete(Long contestId) {
        contestRepository.deleteById(contestId);
    }
}