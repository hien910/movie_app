package com.example.movie_app.repository;

import com.example.movie_app.entity.Episode;
import com.example.movie_app.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    List<Episode> findByMovie_IdOrderByDisplayOrderAsc(Integer movieId);

    List<Episode> findByMovie_IdAndStatusOrderByDisplayOrderAsc(Integer movieId, Boolean status);

    Optional<Episode> findByMovie_IdAndDisplayOrderAndStatus(Integer movieId, int i, boolean episodeStatus);
}
