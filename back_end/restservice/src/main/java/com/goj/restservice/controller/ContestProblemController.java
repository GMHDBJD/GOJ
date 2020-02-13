package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.goj.restservice.entity.ContestProblem;
import com.goj.restservice.entity.Problem;
import com.goj.restservice.entity.ContestProblemId;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.ProblemSummary;
import com.goj.restservice.service.ContestProblemService;
import com.goj.restservice.service.ContestService;
import com.goj.restservice.service.ProblemService;

import static com.goj.restservice.util.Util.checkResourceFound;

@RestController
@RequestMapping(path = "/v1/contests/{contestId}/problems")
@Validated
public class ContestProblemController {
    @Autowired
    private ContestProblemService contestProblemService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private ProblemService problemService;

    private void contestIsExist(Long contestId) throws CustomException {
        ContestDetail contestDetail = contestService.readOne(contestId);
        checkResourceFound(contestDetail);
        if (contestId != contestDetail.getContestId())
            throw new CustomException("contestId doesn't match", HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable("contestId") Long contestId, @Valid @RequestBody ContestProblem newContestProblem,
            HttpServletRequest request, HttpServletResponse response) {

        if (contestId != newContestProblem.getContestId())
            throw new CustomException("contestId doesn't match", HttpStatus.BAD_REQUEST);

        contestIsExist(contestId);

        Problem problem = problemService.readOne(newContestProblem.getProblemId());
        checkResourceFound(problem);

        newContestProblem.setTitle(problem.getTitle());
        ContestProblem createdContestProblem = contestProblemService.create(newContestProblem);

        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdContestProblem.getProblemId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ProblemSummary> readAll(@PathVariable("contestId") Long contestId,
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "30") @Min(value = 1, message = "per_page must be greater than or equal to 1") @Max(value = 100, message = "per_page must be less than or equal to 100") int per_page) {

        contestIsExist(contestId);

        return contestProblemService.readAll(contestId, page - 1, per_page);
    }

    @DeleteMapping("/{problemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("contestId") Long contestId, @PathVariable("problemId") Long problemId,
            HttpServletRequest request, HttpServletResponse response) {

        contestIsExist(contestId);

        ContestProblemId contestProblemId = new ContestProblemId(problemId, contestId);

        contestProblemService.delete(contestProblemId);
    }
}