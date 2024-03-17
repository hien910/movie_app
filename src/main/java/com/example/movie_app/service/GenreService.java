package com.example.movie_app.service;

import com.example.movie_app.entity.Blog;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.exception.ResourceNotFoundException;
import com.example.movie_app.model.request.UpsertGenreRequest;
import com.example.movie_app.model.request.UpsertMovieRequest;
import com.example.movie_app.repository.GenreRepository;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    Slugify slugify = Slugify.builder().build();
    public Genre creatGenre(UpsertGenreRequest request) {
        Genre genre = Genre.builder()
                .name(request.getName())
                .slug(slugify.slugify(request.getName()))
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return genreRepository.save(genre);
    }

    public Genre updateGenre(UpsertGenreRequest request,Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + id));
        genre.setName(request.getName());
        genre.setUpdatedAt(new Date());
        genre.setCreatedAt(new Date());
        genre.setSlug(slugify.slugify(request.getName()));

        return genreRepository.save(genre);
    }

    public void deleteGenre(Integer id) {
            Genre genre = genreRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + id));
            genreRepository.delete(genre);
    }
}
