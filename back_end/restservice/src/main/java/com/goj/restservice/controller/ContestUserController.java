package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;

import com.goj.restservice.entity.Contest;
import com.goj.restservice.entity.ContestUser;
import com.goj.restservice.entity.ContestUserKey;
import com.goj.restservice.entity.User;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.projection.UserSummary;
import com.goj.restservice.repository.ContestRepository;
import com.goj.restservice.repository.ContestUserRepository;

@RestController
@RequestMapping(path = "/v1/contests/{contestId}/users")
@Validated
public class ContestUserController {
    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestUserRepository contestUserRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable("contestId") Long contestId, @RequestBody Map<String, String> contestUserForm,
            HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new CustomException("Contest doesn't exist", HttpStatus.BAD_REQUEST));

        if (contest.getStartTime().isBefore(LocalDateTime.now()))
            throw new CustomException("Contest has begun.", HttpStatus.METHOD_NOT_ALLOWED);


        String password = contestUserForm.get("password");
        if (contest.getPassword() != null && !contest.getPassword().equals(password)) {
            throw new CustomException("invalid password.", HttpStatus.METHOD_NOT_ALLOWED);
        }

        ContestUser contestUser = new ContestUser(new ContestUserKey(contestId, user.getUserId()), contest, user);
        contestUserRepository.save(contestUser);

        response.setHeader("Location", request.getRequestURL().append("/").append(user.getUserId()).toString());

    }

    @GetMapping
    public @ResponseBody Iterable<UserSummary> readAll(@PathVariable("contestId") Long contestId,
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page,
            @AuthenticationPrincipal User user) {

        if (user.getRoles().contains("ROLE_GMH")
                || contestUserRepository.existsById(new ContestUserKey(contestId, user.getUserId())))
            return contestUserRepository.findAllUserSummaryByContestId(contestId, PageRequest.of(page - 1, per_page));
        else
            throw new CustomException("User doesn't join the contest.", HttpStatus.METHOD_NOT_ALLOWED);

    }

}