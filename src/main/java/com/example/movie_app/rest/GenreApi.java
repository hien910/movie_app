package com.example.movie_app.rest;

import com.example.movie_app.entity.Genre;
import com.example.movie_app.entity.Movie;
import com.example.movie_app.model.request.UpsertGenreRequest;
import com.example.movie_app.model.request.UpsertMovieRequest;
import com.example.movie_app.service.GenreService;
import com.example.movie_app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/genres")
@RequiredArgsConstructor
public class GenreApi {
    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<?> creatGenre(@RequestBody UpsertGenreRequest request){
        Genre genre = genreService.creatGenre(request);
        return ResponseEntity.ok(genre);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateGenre(@RequestBody UpsertGenreRequest request,@PathVariable Integer id){
        Genre genre = genreService.updateGenre(request, id);
        return ResponseEntity.ok(genre);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteGenre(@PathVariable Integer id){
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
