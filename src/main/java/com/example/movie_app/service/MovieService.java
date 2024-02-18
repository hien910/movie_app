package com.example.movie_app.service;

import com.example.movie_app.entity.Movie;
import com.example.movie_app.model.enums.MovieType;
import com.example.movie_app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MovieService   {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findByTypeOrderByPublishAtDesc(MovieType movieType) {
        return movieRepository.findByTypeOrderByPublishAtDesc(MovieType.valueOf(String.valueOf(movieType)));
    }
    public Movie findMovieById(Integer id){

        return movieRepository.findMovieById(id);
    }
    public Page<Movie> getHotMovies(Boolean status, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("view").descending()); // page trong jpa bắt đầu từ 0
        return movieRepository.findByStatus(status, pageRequest);
    }
    public List<Movie> findMovieRelated(MovieType movieType){
        return movieRepository.findMoviesByTypeOrderByRatingDesc(MovieType.valueOf(String.valueOf(movieType)));
    }
    public Page<Movie> getMoviesByType(MovieType movieType, Boolean status, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("publishedAt").descending()); // page trong jpa bắt đầu từ 0
        return movieRepository.findByTypeAndStatus(movieType, status, pageRequest);
    }
    public List<Movie> getRelatedMovies(Integer id, MovieType type, Boolean status, Integer size) {
        return movieRepository
                .findByTypeAndStatusAndRatingGreaterThanEqualAndIdNotOrderByRatingDescViewDescPublishedAtDesc(type, status, 5.0, id)
                .stream()
                .limit(size)
                .toList();
    }
}
