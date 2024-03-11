package com.example.movie_app.repository;

import com.example.movie_app.entity.Episode;
import com.example.movie_app.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    List<Episode> findAllByMovieId(Integer id);
}
