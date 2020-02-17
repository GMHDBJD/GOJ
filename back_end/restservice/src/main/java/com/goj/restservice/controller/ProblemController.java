package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.goj.restservice.entity.Problem;
import com.goj.restservice.form.ProblemForm;
import com.goj.restservice.projection.ProblemSummary;
import com.goj.restservice.service.ProblemService;

import com.goj.restservice.util.Util;

@RestController
@RequestMapping(path = "/v1/problems")
@Validated
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    @Autowired
    Util util;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ProblemForm problemForm, HttpServletRequest request,
            HttpServletResponse response) {
        Problem newProblem = new Problem(problemForm.getTitle(), problemForm.getSource(), problemForm.getDescription(),
                problemForm.getInput(), problemForm.getOutput(), problemForm.getSampleInput(),
                problemForm.getSampleOutput(), problemForm.getHint(), problemForm.getTimeLimit(),
                problemForm.getMemoryLimit());
        Problem createProblem = problemService.create(newProblem);
        response.setHeader("Location",
                request.getRequestURL().append("/").append(createProblem.getProblemId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ProblemSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "30") @Min(value = 1, message = "per_page must be greater than or equal to 1") @Max(value = 100, message = "per_page must be less than or equal to 100") int per_page) {
        return problemService.readAll(page - 1, per_page);
    }

    @GetMapping("/{problemId}")
    public @ResponseBody Problem readOne(@PathVariable("problemId") Long problemId) {
        Problem problem = problemService.readOne(problemId);
        util.checkResourceFound(problem);
        return problem;
    }

    @PutMapping("/{problemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("problemId") Long problemId, @Valid @RequestBody ProblemForm problemForm,
            HttpServletRequest request, HttpServletResponse response) {
        Problem newProblem = new Problem(problemForm.getTitle(), problemForm.getSource(), problemForm.getDescription(),
                problemForm.getInput(), problemForm.getOutput(), problemForm.getSampleInput(),
                problemForm.getSampleOutput(), problemForm.getHint(), problemForm.getTimeLimit(),
                problemForm.getMemoryLimit());
        newProblem.setProblemId(problemId);
        problemService.update(newProblem);
    }

    @DeleteMapping("/{problemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("problemId") Long problemId, HttpServletRequest request,
            HttpServletResponse response) {
        problemService.delete(problemId);
    }
}