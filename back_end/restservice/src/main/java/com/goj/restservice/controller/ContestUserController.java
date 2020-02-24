package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.entity.ContestUser;
import com.goj.restservice.entity.User;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.projection.ContestDetail;
import com.goj.restservice.projection.UserSummary;
import com.goj.restservice.repository.ContestRepository;
import com.goj.restservice.repository.ContestUserRepository;
import com.goj.restservice.service.ContestService;
import com.goj.restservice.service.ContestUserService;

import com.goj.restservice.util.Util;

@RestController
@RequestMapping(path = "/v1/contests/{contestId}/users")
@Validated
public class ContestUserController {
    @Autowired
    private ContestUserService contestUserService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestUserRepository contestUserRepository;

    @Autowired
    Util util;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable("contestId") Long contestId, @RequestBody Map<String, String> contestUserForm,
            HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new CustomException("Contest doesn't exist", HttpStatus.BAD_REQUEST));

        String password = contestUserForm.get("password");
        if (contest.getPassword() != null && !contest.getPassword().equals(password)) {
            throw new CustomException("wrong password.", HttpStatus.BAD_REQUEST);
        }

        ContestUser newContestUser = new ContestUser(contestId, user.getUserId());
        ContestUser createdContestUser = contestUserService.create(newContestUser);

        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdContestUser.getUserId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<UserSummary> readAll(@PathVariable("contestId") Long contestId,
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page,
            @AuthenticationPrincipal User user) {

        ContestDetail contestDetail = contestService.readOne(contestId);
        util.checkResourceFound(contestDetail);

        if (contestDetail.getCreateUserUsername().equals(user.getUsername()) || user.getRoles().contains("ROLE_GMH")
                || contestUserRepository.existsByContestIdAndUserId(contestId, user.getUserId())) {
            return contestUserService.readAll(contestId, page - 1, per_page);
        } else {
            throw new CustomException("User doesn't join the contest.", HttpStatus.BAD_REQUEST);
        }
    }

}