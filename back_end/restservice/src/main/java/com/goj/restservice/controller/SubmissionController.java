package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.goj.restservice.entity.Submission;
import com.goj.restservice.projection.SubmissionDetail;
import com.goj.restservice.projection.SubmissionSummary;
import com.goj.restservice.service.SubmissionService;

import static com.goj.restservice.util.Util.checkResourceFound;

@RestController
@RequestMapping(path = "/v1/status")
@Validated
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Submission newSubmission, HttpServletRequest request,
            HttpServletResponse response) {
        newSubmission.setSubmissionId(null);
        Submission createdSubmission = submissionService.create(newSubmission);
        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdSubmission.getSubmissionId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<SubmissionSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "30") @Min(value = 1, message = "per_page must be greater than or equal to 1") @Max(value = 100, message = "per_page must be lower than or equal to 100") int per_page) {
        return submissionService.readAll(page - 1, per_page);
    }

    @GetMapping("/{submissionId}")
    public @ResponseBody SubmissionDetail readOne(@PathVariable("submissionId") Long submissionId) {
        SubmissionDetail submissionDetail = submissionService.readOne(submissionId);
        checkResourceFound(submissionDetail);
        return submissionDetail;
    }
}