package com.example.movie_app.rest;

import com.example.movie_app.entity.Blog;
import com.example.movie_app.entity.Movie;
import com.example.movie_app.model.request.UpsertBlogRequest;
import com.example.movie_app.model.request.UpsertMovieRequest;
import com.example.movie_app.service.BlogService;
import com.example.movie_app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/movies")
@RequiredArgsConstructor
public class MovieApi {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody UpsertMovieRequest request){
        Movie movie = movieService.creatMovie(request);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateBlog(@RequestBody UpsertMovieRequest request,@PathVariable Integer id){
        Movie movie = movieService.updateMovie(request,id);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteBlog(@PathVariable Integer id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
