package com.example.movie_app.repository;

import com.example.movie_app.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    List<Genre> findAllByOrderByCreatedAtDesc();
}
