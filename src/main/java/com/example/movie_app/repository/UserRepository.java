package com.example.movie_app.repository;

import com.example.movie_app.entity.Movie;
import com.example.movie_app.entity.User;
import com.example.movie_app.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUserById(Integer id);
    List<User> findUserByRole(UserRole role);

    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole userRole);

    List<User> findUserByCreatedAtBetweenOrderByCreatedAtDesc(Date start, Date end);
}
