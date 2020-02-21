package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.goj.restservice.repository.ContestUserRepository;
import com.goj.restservice.service.ContestService;
import com.goj.restservice.util.Util;

@RestController
@RequestMapping(path = "/v1/contests")
@Validated
public class ContestController {
    @Autowired
    private ContestService contestService;

    @Autowired
    Util util;

    @Autowired
    ContestUserRepository contestUserRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ContestForm contestForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        Contest newContest = new Contest(contestForm.getTitle(), contestForm.getDescription(),
                contestForm.getStartTime(), contestForm.getEndTime(), contestForm.getPassword());

        newContest.setCreateUser(user);
        Contest createdContest = contestService.create(newContest);
        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdContest.getContestId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<ContestSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page) {
        return contestService.readAll(page - 1, per_page);
    }

    @GetMapping("/{contestId}")
    public @ResponseBody ContestDetail readOne(@PathVariable("contestId") Long contestId,
            @AuthenticationPrincipal User user) {
        ContestDetail contestDetail = contestService.readOne(contestId);
        util.checkResourceFound(contestDetail);

        if (contestDetail.getCreateUserUsername().equals(user.getUsername()) || user.getRoles().contains("ROLE_GMH"))
            return contestDetail;

        if (!contestUserRepository.existsByContestIdAndUserId(contestId, user.getUserId()))
            throw new CustomException("User doesn't join the contest.", HttpStatus.BAD_REQUEST);

        return contestDetail;
    }

    @PutMapping("/{contestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("contestId") Long contestId, @Valid @RequestBody ContestForm contestForm,
            HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {
        Contest newContest = new Contest(contestForm.getTitle(), contestForm.getDescription(),
                contestForm.getStartTime(), contestForm.getEndTime(), contestForm.getPassword());

        newContest.setCreateUser(user);
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