package com.example.movie_app.service;

import com.example.movie_app.entity.Movie;
import com.example.movie_app.model.enums.MovieType;
import com.example.movie_app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Movie> findMovieHot(){
        return movieRepository.findMovieOrderByRatingDesc();
    }
    public List<Movie> findMovieRelated(MovieType movieType){
        return movieRepository.findMoviesByTypeOrderByRatingDesc(MovieType.valueOf(String.valueOf(movieType)));
    }
}
