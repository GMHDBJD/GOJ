package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.entity.ContestUser;
import com.goj.restservice.entity.ContestUserId;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.projection.UserDetail;
import com.goj.restservice.projection.UserSummary;
import com.goj.restservice.repository.ContestRepository;
import com.goj.restservice.service.ContestUserService;
import com.goj.restservice.service.UserService;

import com.goj.restservice.util.Util;

@RestController
@RequestMapping(path = "/v1/contests/{contestId}/users")
@Validated
public class ContestUserController {
    @Autowired
    private ContestUserService contestUserService;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    Util util;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable("contestId") Long contestId,
            @NotNull(message = "username could not be null") String username, String password,
            HttpServletRequest request, HttpServletResponse response) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new CustomException("Contest doesn't exist", HttpStatus.BAD_REQUEST));

        if (contest.getPassword() != null && !contest.getPassword().equals(password)) {
            throw new CustomException("wrong password.", HttpStatus.BAD_REQUEST);
        }

        UserDetail userDetail = userService.readOne(username);
        util.checkResourceFound(userDetail);

        ContestUser newContestUser = new ContestUser(contestId, userDetail.getUserId());
        ContestUser createdContestUser = contestUserService.create(newContestUser);

        response.setHeader("Location",
                request.getRequestURL().append("/").append(createdContestUser.getUserId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<UserSummary> readAll(@PathVariable("contestId") Long contestId,
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "30") @Min(value = 1, message = "per_page must be greater than or equal to 1") @Max(value = 100, message = "per_page must be less than or equal to 100") int per_page) {

        if (contestRepository.existsById(contestId))
            throw new CustomException("Contest doesn't match.", HttpStatus.BAD_REQUEST);

        return contestUserService.readAll(contestId, page - 1, per_page);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("contestId") Long contestId, @PathVariable("userId") Long userId,
            HttpServletRequest request, HttpServletResponse response) {

        if (contestRepository.existsById(contestId))
            throw new CustomException("Contest doesn't match.", HttpStatus.BAD_REQUEST);

        ContestUserId contestUserId = new ContestUserId(userId, contestId);

        contestUserService.delete(contestUserId);
    }
}