package com.example.movie_app.repository;

import com.example.movie_app.entity.Director;
import com.example.movie_app.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {
    List<Director> findAllByOrderByCreatedAtDesc();
}
