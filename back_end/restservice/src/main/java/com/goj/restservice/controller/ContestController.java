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

import com.goj.restservice.entity.Contest;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.ContestSummary;
import com.goj.restservice.service.ContestService;

import static com.goj.restservice.util.Util.checkResourceFound;

@RestController
@RequestMapping(path = "/v1/contests")
@Validated
public class ContestController {
    @Autowired
    private ContestService contestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Contest newContest, HttpServletRequest request,
            HttpServletResponse response) {
        newContest.setContestId(null);
        Contest createdContest = contestService.create(newContest);
        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdContest.getContestId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ContestSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "30") @Min(value = 1, message = "per_page must be greater than or equal to 1") @Max(value = 100, message = "per_page must be lower than or equal to 100") int per_page) {
        return contestService.readAll(page - 1, per_page);
    }

    @GetMapping("/{contestId}")
    public @ResponseBody ContestDetail readOne(@PathVariable("contestId") Long contestId) {
        ContestDetail contestDetail = contestService.readOne(contestId);
        checkResourceFound(contestDetail);
        return contestDetail;
    }

    @PutMapping("/{contestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("contestId") Long contestId, @Valid @RequestBody Contest newContest,
            HttpServletRequest request, HttpServletResponse response) {
        if (contestId != newContest.getContestId())
            throw new CustomException("contestId doesn't match", HttpStatus.BAD_REQUEST);
        checkResourceFound(contestService.readOne(contestId));
        contestService.update(newContest);
    }

    @DeleteMapping("/{contestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("contestId") Long contestId, HttpServletRequest request,
            HttpServletResponse response) {
        contestService.delete(contestId);
    }
}