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
import com.goj.restservice.entity.ContestProblem;
import com.goj.restservice.entity.Problem;
import com.goj.restservice.entity.User;
import com.goj.restservice.entity.ContestProblemKey;
import com.goj.restservice.entity.ContestUserKey;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.form.ContestProblemForm;
import com.goj.restservice.projection.ProblemSummary;
import com.goj.restservice.repository.ContestProblemRepository;
import com.goj.restservice.repository.ContestRepository;
import com.goj.restservice.repository.ContestUserRepository;
import com.goj.restservice.repository.ProblemRepository;

@RestController
@RequestMapping(path = "/api/v1/contests/{contestId}/problems")
@Validated
public class ContestProblemController {
    @Autowired
    private ContestProblemRepository contestProblemRepository;

    @Autowired
    private ContestUserRepository contestUserRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable("contestId") Long contestId,
            @Valid @RequestBody ContestProblemForm contestProblemForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {

        Long problemId = contestProblemForm.getProblemId();

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));

        ContestProblem newContestProblem = new ContestProblem(new ContestProblemKey(contestId, problemId), contest,
                problem, contestProblemForm.getTitle());
        contestProblemRepository.save(newContestProblem);

        response.setHeader("Location", request.getRequestURL().append("/").append(problemId).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ProblemSummary> readAll(@PathVariable("contestId") Long contestId,
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page,
            @AuthenticationPrincipal User user) {

        if (user.getRoles().contains("ROLE_ADMIN")
                || contestUserRepository.existsById(new ContestUserKey(contestId, user.getUserId())))
            return contestProblemRepository.findAllProblemSummaryByContestIdAndUserId(contestId, user.getUserId(),
                    PageRequest.of(page - 1, per_page));
        else
            throw new CustomException("User doesn't join the contest.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{problemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("contestId") Long contestId, @PathVariable("problemId") Long problemId,
            HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {

        contestProblemRepository.deleteById(new ContestProblemKey(contestId, problemId));
    }
}