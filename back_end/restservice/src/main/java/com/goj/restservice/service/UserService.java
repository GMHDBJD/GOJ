package com.goj.restservice.service;

import java.util.Optional;

import com.goj.restservice.entity.User;
import com.goj.restservice.projection.UserDetail;
import com.goj.restservice.projection.UserSummary;
import com.goj.restservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(User newUser) {
        return userRepository.save(newUser);
    }

    public Iterable<UserSummary> readAll(int page, int per_page) {
        Page<UserSummary> pageResult = userRepository.findAllUserSummaryBy(PageRequest.of(page, per_page));
        return pageResult.getContent();
    }

    public UserDetail readOne(String username) {
        Optional<UserDetail> result = userRepository.findUserDetailByUsername(username);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public void update(User newUser) {
        userRepository.save(newUser);
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }
}