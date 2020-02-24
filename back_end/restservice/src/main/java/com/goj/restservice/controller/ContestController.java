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
import com.goj.restservice.entity.User;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.form.ContestForm;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.ContestSummary;
import com.goj.restservice.projection.SubmissionSummary;
import com.goj.restservice.repository.ContestRepository;
import com.goj.restservice.repository.ContestUserRepository;
import com.goj.restservice.repository.SubmissionRepository;
import com.goj.restservice.service.ContestService;
import com.goj.restservice.util.Util;

@RestController
@RequestMapping(path = "/v1/contests")
@Validated
public class ContestController {
    @Autowired
    private ContestService contestService;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    Util util;

    @Autowired
    private ContestUserRepository contestUserRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ContestForm contestForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        Contest newContest = new Contest();
        newContest.update(contestForm.getTitle(), contestForm.getDescription(), contestForm.getStartTime(),
                contestForm.getEndTime(), contestForm.getPassword());

        newContest.setCreateUser(user);
        Contest createdContest = contestService.create(newContest);
        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdContest.getContestId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ContestSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page,
            @AuthenticationPrincipal User user) {
        if (user == null)
            return contestService.readAll(page - 1, per_page);
        else
            return contestRepository.findAllContestSummaryByUserId(user.getUserId(),
                    PageRequest.of(page - 1, per_page));
    }

    @GetMapping("/{contestId}/submissions")
    public @ResponseBody Iterable<SubmissionSummary> readAllSubmissions(@PathVariable("contestId") Long contestId,
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page,
            @AuthenticationPrincipal User user) {

        ContestDetail contestDetail = contestService.readOne(contestId);
        util.checkResourceFound(contestDetail);

        if (contestDetail.getCreateUserUsername().equals(user.getUsername()) || user.getRoles().contains("ROLE_GMH")
                || contestUserRepository.existsByContestIdAndUserId(contestId, user.getUserId())) {
            return submissionRepository.findAllSubmissionSummaryByContestId(contestId,
                    PageRequest.of(page - 1, per_page));
        } else {
            throw new CustomException("User doesn't join the contest.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{contestId}")
    public @ResponseBody ContestDetail readOne(@PathVariable("contestId") Long contestId,
            @AuthenticationPrincipal User user) {
        ContestDetail contestDetail = contestService.readOne(contestId);
        util.checkResourceFound(contestDetail);

        if (contestDetail.getCreateUserUsername().equals(user.getUsername()) || user.getRoles().contains("ROLE_GMH")
                || contestUserRepository.existsByContestIdAndUserId(contestId, user.getUserId()))
            return contestDetail;
        else
            throw new CustomException("User doesn't join the contest.", HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/{contestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("contestId") Long contestId, @Valid @RequestBody ContestForm contestForm,
            HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {

        Contest contest = new Contest();
        if (contestRepository.existsById(contestId)) {
            contest = contestRepository.findById(contestId).get();
            if (contest.getCreateUser().getUserId() != user.getUserId() && !user.getRoles().contains("ROLE_GMH"))
                throw new CustomException("Method not allow", HttpStatus.BAD_REQUEST);
        }
        contest.update(contestForm.getTitle(), contestForm.getDescription(), contestForm.getStartTime(),
                contestForm.getEndTime(), contestForm.getPassword());

        contest.setContestId(contestId);
        contestService.update(contest);
    }

    @DeleteMapping("/{contestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("contestId") Long contestId, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {

        if (contestRepository.existsById(contestId)) {
            Contest contest = contestRepository.findById(contestId).get();
            if (contest.getCreateUser().getUserId() != user.getUserId() && !user.getRoles().contains("ROLE_GMH"))
                throw new CustomException("Method not allow", HttpStatus.BAD_REQUEST);

            contestService.delete(contestId);
        }
    }
}