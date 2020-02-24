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

import com.goj.restservice.entity.Problem;
import com.goj.restservice.entity.User;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.form.ProblemForm;
import com.goj.restservice.projection.ProblemDetail;
import com.goj.restservice.projection.ProblemSummary;
import com.goj.restservice.repository.ProblemRepository;

@RestController
@RequestMapping(path = "/v1/problems")
@Validated
public class ProblemController {

    @Autowired
    private ProblemRepository problemRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ProblemForm problemForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {

        Problem newProblem = new Problem(problemForm.getTitle(), problemForm.getSource(), problemForm.getDescription(),
                problemForm.getInput(), problemForm.getOutput(), problemForm.getSampleInput(),
                problemForm.getSampleOutput(), problemForm.getHint(), problemForm.getTimeLimit(),
                problemForm.getMemoryLimit());

        Problem createProblem = problemRepository.save(newProblem);
        response.setHeader("Location",
                request.getRequestURL().append("/").append(createProblem.getProblemId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ProblemSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page,
            @AuthenticationPrincipal User user) {
        if (user == null)
            return problemRepository.findAllProblemSummaryBy(PageRequest.of(page - 1, per_page));
        else {
            return problemRepository.findAllProblemSummaryByUserId(user.getUserId(),
                    PageRequest.of(page - 1, per_page));
        }
    }

    @GetMapping("/{problemId}")
    public @ResponseBody ProblemDetail readOne(@PathVariable("problemId") Long problemId) {
        return problemRepository.findProblemDetailByProblemId(problemId)
                .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{problemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("problemId") Long problemId, @Valid @RequestBody ProblemForm problemForm,
            HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {

        Problem problem = problemRepository.findById(problemId).orElse(null);

        if (problem == null) {
            problem = new Problem(problemForm.getTitle(), problemForm.getSource(), problemForm.getDescription(),
                    problemForm.getInput(), problemForm.getOutput(), problemForm.getSampleInput(),
                    problemForm.getSampleOutput(), problemForm.getHint(), problemForm.getTimeLimit(),
                    problemForm.getMemoryLimit());
            problem.setProblemId(problemId);
        } else {
            problem.update(problemForm.getTitle(), problemForm.getSource(), problemForm.getDescription(),
                    problemForm.getInput(), problemForm.getOutput(), problemForm.getSampleInput(),
                    problemForm.getSampleOutput(), problemForm.getHint(), problemForm.getTimeLimit(),
                    problemForm.getMemoryLimit());
        }
        problemRepository.save(problem);
    }

    @DeleteMapping("/{problemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("problemId") Long problemId, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        problemRepository.deleteById(problemId);
    }
}