package com.example.movie_app.service;

import com.example.movie_app.entity.User;
import com.example.movie_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> findUserOfMonth(Date start, Date end){
        return userRepository.findUserByCreatedAtBetweenOrderByCreatedAtDesc(start, end);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
