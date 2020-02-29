package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.goj.restservice.entity.User;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.form.AddRoleForm;
import com.goj.restservice.form.ResetNicknameForm;
import com.goj.restservice.form.ResetPasswordForm;
import com.goj.restservice.projection.UserDetail;
import com.goj.restservice.projection.UserSummary;
import com.goj.restservice.repository.UserRepository;

@RestController
@RequestMapping(path = "/api/v1/users")
@Validated
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public @ResponseBody Iterable<UserSummary> readAll(
            @RequestParam(value = "page", defaultValue = "1") @Min(value = 1, message = "page must be greater than or equal to 1") int page,
            @RequestParam(value = "per_page", defaultValue = "10000") @Min(value = 1, message = "per_page must be greater than or equal to 1") int per_page) {
        return userRepository.findAllUserSummaryBy(PageRequest.of(page - 1, per_page));
    }

    @GetMapping("/{username}")
    public @ResponseBody UserDetail readOne(@PathVariable("username") String username) {
        return userRepository.findUserDetailByUsername(username)
                .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{username}/reset_password")
    @ResponseStatus(HttpStatus.CREATED)
    public void resetPassword(@PathVariable("username") String username,
            @Valid @RequestBody ResetPasswordForm resetPasswordForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {

        if (!user.getUsername().equals(username))
            throw new CustomException("Method not allowed.", HttpStatus.METHOD_NOT_ALLOWED);

        user.setPassword(passwordEncoder.encode(resetPasswordForm.getNewPassword()));

        userRepository.save(user);
    }

    @PostMapping("/{username}/reset_nickname")
    @ResponseStatus(HttpStatus.CREATED)
    public void resetNickname(@PathVariable("username") String username,
            @Valid @RequestBody ResetNicknameForm resetNicknameForm, HttpServletRequest request,
            HttpServletResponse response, @AuthenticationPrincipal User user) {
        if (!user.getUsername().equals(username))
            throw new CustomException("Method not allowed.", HttpStatus.METHOD_NOT_ALLOWED);

        user.setNickname(resetNicknameForm.getNewNickname());

        userRepository.save(user);
    }

    @PostMapping("/{username}/add_role")
    @ResponseStatus(HttpStatus.CREATED)
    public void resetNickname(@PathVariable("username") String username, @Valid @RequestBody AddRoleForm addRoleForm,
            HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {

        short role = addRoleForm.getRole();

        User updateUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("Resource not found.", HttpStatus.NOT_FOUND));

        String[] array = { "ROLE_ADMIN", "ROLE_GMH" };

        if (!user.getRoles().contains(array[role]))
            throw new CustomException("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);

        if (!updateUser.getRoles().contains(array[role])) {
            updateUser.getRoles().add(array[role]);
            userRepository.save(updateUser);
        }
    }
}