package com.example.movie_app.repository;

import com.example.movie_app.entity.Episode;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

}
