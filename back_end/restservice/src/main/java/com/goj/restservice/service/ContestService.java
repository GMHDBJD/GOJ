package com.goj.restservice.service;

import java.util.Optional;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.ContestSummary;
import com.goj.restservice.repository.ContestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ContestService {
    @Autowired
    private ContestRepository contestRepository;

    public Contest create(Contest newContest) {
        return contestRepository.save(newContest);
    }

    public Iterable<ContestSummary> readAll(int page, int per_page) {
        Page<ContestSummary> pageResult = contestRepository
                .findAllContestSummaryBy(PageRequest.of(page, per_page));
        return pageResult.getContent();
    }

    public ContestDetail readOne(Long contestId) {
        Optional<ContestDetail> result = contestRepository.findByContestId(contestId);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public void update(Contest newContest) {
        contestRepository.save(newContest);
    }

    public void delete(Long contestId) {
        contestRepository.deleteById(contestId);
    }
}