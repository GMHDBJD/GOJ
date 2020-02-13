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

import com.goj.restservice.entity.User;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.form.ResetPasswordForm;
import com.goj.restservice.projection.UserDetail;
import com.goj.restservice.projection.UserSummary;
import com.goj.restservice.service.UserService;
import com.goj.restservice.util.Util;

@RestController
@RequestMapping(path = "/v1/users")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Util util;

    @GetMapping
    public @ResponseBody Iterable<UserSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "30") @Min(value = 1, message = "per_page must be greater than or equal to 1") @Max(value = 100, message = "per_page must be less than or equal to 100") int per_page) {
        return userService.readAll(page - 1, per_page);
    }

    @GetMapping("/{username}")
    public @ResponseBody UserDetail readOne(@PathVariable("username") String username) {
        UserDetail userDetail = userService.readOne(username);
        util.checkResourceFound(userDetail);
        return userDetail;
    }

    @PostMapping("/{username}/reset_password")
    @ResponseStatus(HttpStatus.CREATED)
    public void resetPassword(@PathVariable("username") String username,
            @Valid @RequestBody ResetPasswordForm resetPasswordForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        if (!user.getUsername().equals(username))
            throw new CustomException("Method not allowed.", HttpStatus.METHOD_NOT_ALLOWED);

        user.setPassword(passwordEncoder.encode(resetPasswordForm.getNewPassword()));

        userService.update(user);
    }

    @PostMapping("/{username}/reset_nickname")
    @ResponseStatus(HttpStatus.CREATED)
    public void resetNickname(@PathVariable("username") String username,
            @Valid @RequestParam("newNickname") String newNickname, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        if (user.getUsername().equals(username))
            throw new CustomException("Method not allowed.", HttpStatus.METHOD_NOT_ALLOWED);

        user.setNickname(newNickname);
        userService.update(user);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("username") String username, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        if (user.getUsername() != username)
            throw new CustomException("Method not allowed.", HttpStatus.METHOD_NOT_ALLOWED);

        userService.delete(username);
    }
}