package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.goj.restservice.entity.ContestProblem;
import com.goj.restservice.entity.Problem;
import com.goj.restservice.entity.User;
import com.goj.restservice.entity.ContestProblemId;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.form.ContestProblemForm;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.ProblemSummary;
import com.goj.restservice.repository.ContestUserRepository;
import com.goj.restservice.service.ContestProblemService;
import com.goj.restservice.service.ContestService;
import com.goj.restservice.service.ProblemService;

import com.goj.restservice.util.Util;

@RestController
@RequestMapping(path = "/v1/contests/{contestId}/problems")
@Validated
public class ContestProblemController {
    @Autowired
    private ContestProblemService contestProblemService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private ContestUserRepository contestUserRepository;

    @Autowired
    private ProblemService problemService;

    @Autowired
    Util util;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable("contestId") Long contestId,
            @Valid @RequestBody ContestProblemForm contestProblemForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {

        Long problemId = contestProblemForm.getProblemId();

        ContestDetail contestDetail = contestService.readOne(contestId);
        util.checkResourceFound(contestDetail);

        if (!contestDetail.getCreateUserUsername().equals(user.getUsername()) && !user.getRoles().contains("ROLE_GMH"))
            throw new CustomException("Method not allow.", HttpStatus.BAD_REQUEST);

        Problem problem = problemService.readOne(problemId);
        util.checkResourceFound(problem);

        ContestProblem newContestProblem = new ContestProblem(contestId, problemId, problem.getTitle());
        ContestProblem createdContestProblem = contestProblemService.create(newContestProblem);

        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdContestProblem.getProblemId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ProblemSummary> readAll(@PathVariable("contestId") Long contestId,
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page,
            @AuthenticationPrincipal User user) {

        ContestDetail contestDetail = contestService.readOne(contestId);
        util.checkResourceFound(contestDetail);

        if (contestDetail.getCreateUserUsername().equals(user.getUsername()) || user.getRoles().contains("ROLE_GMH")
                || contestUserRepository.existsByContestIdAndUserId(contestId, user.getUserId())) {
            return contestProblemService.readAll(contestId, user.getUserId(), page - 1, per_page);
        } else {
            throw new CustomException("User doesn't join the contest.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{problemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("contestId") Long contestId, @PathVariable("problemId") Long problemId,
            HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {

        ContestDetail contestDetail = contestService.readOne(contestId);
        util.checkResourceFound(contestDetail);

        if (contestDetail.getCreateUserUsername().equals(user.getUsername()) || user.getRoles().contains("ROLE_GMH")) {

            ContestProblemId contestProblemId = new ContestProblemId(contestId, problemId);
            contestProblemService.delete(contestProblemId);
        } else {
            throw new CustomException("Method not allow.", HttpStatus.BAD_REQUEST);
        }
    }
}