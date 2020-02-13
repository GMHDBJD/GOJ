package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.goj.restservice.entity.User;
import com.goj.restservice.form.ContestForm;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.ContestSummary;
import com.goj.restservice.service.ContestService;
import com.goj.restservice.util.Util;

@RestController
@RequestMapping(path = "/v1/contests")
@Validated
public class ContestController {
    @Autowired
    private ContestService contestService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Util util;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ContestForm contestForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        Contest newContest = new Contest(contestForm.getTitle(), contestForm.getDescription(),
                contestForm.getStartTime(), contestForm.getEndTime());

        if (contestForm.getPassword() != null) {
            newContest.setPassword(passwordEncoder.encode(contestForm.getPassword()));
        }

        newContest.setCreateUser(user);

        Contest createdContest = contestService.create(newContest);
        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdContest.getContestId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ContestSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "30") @Min(value = 1, message = "per_page must be greater than or equal to 1") @Max(value = 100, message = "per_page must be less than or equal to 100") int per_page) {
        return contestService.readAll(page - 1, per_page);
    }

    @GetMapping("/{contestId}")
    public @ResponseBody ContestDetail readOne(@PathVariable("contestId") Long contestId) {
        ContestDetail contestDetail = contestService.readOne(contestId);
        util.checkResourceFound(contestDetail);
        return contestDetail;
    }

    @PutMapping("/{contestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("contestId") Long contestId, @Valid @RequestBody ContestForm contestForm,
            HttpServletRequest request, HttpServletResponse response) {
        Contest newContest = new Contest(contestForm.getTitle(), contestForm.getDescription(),
                contestForm.getStartTime(), contestForm.getEndTime());

        if (contestForm.getPassword() != null) {
            newContest.setPassword(passwordEncoder.encode(contestForm.getPassword()));
        }
        
        newContest.setContestId(contestId);
        contestService.update(newContest);
    }

    @DeleteMapping("/{contestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("contestId") Long contestId, HttpServletRequest request,
            HttpServletResponse response) {
        contestService.delete(contestId);
    }
}