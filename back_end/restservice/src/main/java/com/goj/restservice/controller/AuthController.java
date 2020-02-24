package com.goj.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goj.restservice.entity.User;
import com.goj.restservice.exception.CustomException;
import com.goj.restservice.form.SignupForm;
import com.goj.restservice.repository.UserRepository;
import com.goj.restservice.security.JwtTokenProvider;

import static org.springframework.http.ResponseEntity.ok;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping(path = "/v1/auth")
@Validated
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(path = "/signin")
    public ResponseEntity<Map<Object, Object>> siginin(@RequestBody Map<String, String> signinForm,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = signinForm.get("username");
            String password = signinForm.get("password");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            Set<String> roles = userRepository.findByUsername(username).get().getRoles();

            String token = jwtTokenProvider.createToken(username, roles);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("roles", roles);
            return ok(model);
        } catch (Exception e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void siginup(@Valid @RequestBody SignupForm signupForm, HttpServletRequest request,
            HttpServletResponse response) {
        if (userRepository.existsByUsername(signupForm.getUsername())) {
            throw new CustomException("Username is already taken!", HttpStatus.CONFLICT);
        }

        if (userRepository.existsByEmail(signupForm.getEmail())) {
            throw new CustomException("Email Address already in use!", HttpStatus.CONFLICT);
        }

        User newUser = new User(signupForm.getUsername(), signupForm.getNickname(), signupForm.getEmail(),
                passwordEncoder.encode(signupForm.getPassword()));

        userRepository.save(newUser);
        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/{username}").buildAndExpand(signupForm.getUsername()).toUri().toString());
    }

    @GetMapping("/validate_token")
    @ResponseStatus(HttpStatus.OK)
    public void validateToken(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            return;
        } else {
            throw new CustomException("Invalid token.", HttpStatus.UNAUTHORIZED);
        }
    }
}