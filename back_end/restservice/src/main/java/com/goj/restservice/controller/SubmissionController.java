package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
import com.goj.restservice.entity.ContestProblemKey;
import com.goj.restservice.entity.Problem;
import com.goj.restservice.entity.SourceCode;
import com.goj.restservice.entity.Submission;
import com.goj.restservice.entity.User;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.form.SubmissionForm;
import com.goj.restservice.projection.SubmissionDetail;
import com.goj.restservice.projection.SubmissionSummary;
import com.goj.restservice.repository.ContestProblemRepository;
import com.goj.restservice.repository.ContestRepository;
import com.goj.restservice.repository.ProblemRepository;
import com.goj.restservice.repository.SourceCodeRepository;
import com.goj.restservice.repository.SubmissionRepository;

@RestController
@RequestMapping(path = "/v1/submissions")
@Validated
public class SubmissionController {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestProblemRepository contestProblemRepository;

    @Autowired
    private SourceCodeRepository sourceCodeRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody SubmissionForm submissionForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {

        Problem problem = problemRepository.findById(submissionForm.getProblemId())
                .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));

        ContestProblem contestProblem = null;
        Contest contest = null;
        if (submissionForm.getContestId() != null) {
            contestProblem = contestProblemRepository
                    .findById(new ContestProblemKey(submissionForm.getContestId(), submissionForm.getProblemId()))
                    .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));
            contest = contestRepository.findById(submissionForm.getContestId()).get();
            /*
            contest.getContestProblemSet().remove(contestProblem);
            contestProblem.setSubmit(contestProblem.getSubmit() + 1);
            contest.getContestProblemSet().add(contestProblem);
            */
        }

        problem.setSubmit(problem.getSubmit() + 1);
        user.setSubmit(user.getSubmit() + 1);

        Submission submission = new Submission(problem, user, submissionForm.getLanguage());

        SourceCode sourceCode = new SourceCode(submission, submissionForm.getCode());

        sourceCodeRepository.save(sourceCode);

        response.setHeader("Location",
                request.getRequestURL().append("/").append(submission.getSubmissionId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<SubmissionSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page) {
        return submissionRepository.findAllSubmissionSummaryBy(PageRequest.of(page - 1, per_page));
    }

    @GetMapping("/{submissionId}")
    public @ResponseBody SubmissionDetail readOne(@PathVariable("submissionId") Long submissionId,
            @AuthenticationPrincipal User user) {
        SubmissionDetail submissionDetail = submissionRepository.findSubmissionDetailBySubmissionId(submissionId)
                .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));
        if (!user.getUsername().equals(submissionDetail.getUserUsername()) && !user.getRoles().contains("ROLE_ADMIN"))
            throw new CustomException("Method not allowed.", HttpStatus.METHOD_NOT_ALLOWED);
        return submissionDetail;
    }
}