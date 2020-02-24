package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.entity.ContestUserKey;
import com.goj.restservice.entity.User;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.form.ContestForm;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.ContestSummary;
import com.goj.restservice.projection.SubmissionSummary;
import com.goj.restservice.repository.ContestRepository;
import com.goj.restservice.repository.ContestUserRepository;
import com.goj.restservice.repository.SubmissionRepository;

@RestController
@RequestMapping(path = "/v1/contests")
@Validated
public class ContestController {
    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestUserRepository contestUserRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ContestForm contestForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        Contest newContest = new Contest(contestForm.getTitle(), contestForm.getDescription(),
                contestForm.getStartTime(), contestForm.getEndTime(), contestForm.getPassword());

        Contest createdContest = contestRepository.save(newContest);
        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdContest.getContestId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ContestSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page,
            @AuthenticationPrincipal User user) {
        if (user == null)
            return contestRepository.findAllContestSummaryBy(PageRequest.of(page - 1, per_page));
        else
            return contestRepository.findAllContestSummaryByUserId(user.getUserId(),
                    PageRequest.of(page - 1, per_page));
    }

    @GetMapping("/{contestId}/submissions")
    public @ResponseBody Iterable<SubmissionSummary> readAllContestSubmissions(
            @PathVariable("contestId") Long contestId,
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page,
            @AuthenticationPrincipal User user) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));

        if (user.getRoles().contains("ROLE_ADMIN")
                || contestUserRepository.existsById(new ContestUserKey(contestId, user.getUserId())))
            return submissionRepository.findAllSubmissionSummaryByContest(contest, PageRequest.of(page - 1, per_page));
        else
            throw new CustomException("User doesn't join the contest.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/{contestId}")
    public @ResponseBody ContestDetail readOne(@PathVariable("contestId") Long contestId,
            @AuthenticationPrincipal User user) {
        ContestDetail contestDetail = contestRepository.findContestDetailByContestId(contestId)
                .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));

        if (user.getRoles().contains("ROLE_ADMIN")
                || contestUserRepository.existsById(new ContestUserKey(contestId, user.getUserId())))
            return contestDetail;
        else
            throw new CustomException("User doesn't join the contest.", HttpStatus.METHOD_NOT_ALLOWED);

    }

    @PutMapping("/{contestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("contestId") Long contestId, @Valid @RequestBody ContestForm contestForm,
            HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {

        Contest contest = contestRepository.findById(contestId).orElse(null);

        if (contest == null) {
            contest = new Contest(contestForm.getTitle(), contestForm.getDescription(), contestForm.getStartTime(),
                    contestForm.getEndTime(), contestForm.getPassword());
            contest.setContestId(contestId);
        } else {
            contest.update(contestForm.getTitle(), contestForm.getDescription(), contestForm.getStartTime(),
                    contestForm.getEndTime(), contestForm.getPassword());
        }

        contestRepository.save(contest);
    }

    @DeleteMapping("/{contestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("contestId") Long contestId, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        contestRepository.deleteById(contestId);
    }
}