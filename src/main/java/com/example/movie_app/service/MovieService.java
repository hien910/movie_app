package com.example.movie_app.service;

import com.example.movie_app.entity.*;
import com.example.movie_app.exception.ResourceNotFoundException;
import com.example.movie_app.model.enums.MovieType;
import com.example.movie_app.model.request.UpsertMovieRequest;
import com.example.movie_app.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService   {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;

    public List<Movie> findByTypeOrderByPublishAtDesc(MovieType movieType) {
        return movieRepository.findByTypeOrderByPublishAtDesc(MovieType.valueOf(String.valueOf(movieType)));
    }
    public Movie findMovieById(Integer id){
        return movieRepository.findMovieById(id);
    }
    public Movie getMovie(Integer id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim với id = " + id));
    }
    public Movie getMovie(Integer id, String slug, Boolean status) {
        return movieRepository.findByIdAndSlugAndStatus(id, slug, status).orElse(null);
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

    public List<Movie> findAll() {
        return movieRepository.findAll(Sort.by("createdAt").descending());
    }

    public Movie getMovieById(Integer id) {
        return movieRepository.findMovieById(id);
    }
    Slugify slugify = Slugify.builder().build();
    public Movie creatMovie(UpsertMovieRequest request) {
        // Lấy danh sách đạo diễn, diễn viên, thể loại từ request
        List<Director> directors = directorRepository.findAllById(request.getDirectorIds());
        List<Actor> actors = actorRepository.findAllById(request.getActorIds());
        List<Genre> genres = genreRepository.findAllById(request.getGenreIds());

        Boolean status = request.getStatus();
        Date publishAt = null;
        if(status){
            publishAt = new Date();
        }
        Faker faker = new Faker();

        // Bổ sung các thông tin khác cho movie từ request
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .directors(directors)
                .actors(actors)
                .genres(genres)
                .type(request.getType())
                .createdAt(new Date())
                .publishedAt(publishAt)
                .updatedAt(new Date())
                .releaseYear(request.getReleaseYear())
                .slug(slugify.slugify(request.getTitle()))
                .description(request.getDescription())
                .status(status)
                .poster(faker.company().logo())
                .build();

        movieRepository.save(movie);
        return movie;
    }

    public Movie updateMovie(UpsertMovieRequest request, Integer id) {

        Movie movie  =  movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim với id = " + id));


        // Lấy danh sách đạo diễn, diễn viên, thể loại từ request
        List<Director> directors = directorRepository.findAllById(request.getDirectorIds());
        List<Actor> actors = actorRepository.findAllById(request.getActorIds());
        List<Genre> genres = genreRepository.findAllById(request.getGenreIds());

        Boolean status = request.getStatus();
        Date publishAt = null;
        if(status){
            publishAt = new Date();
        }

        // Bổ sung các thông tin khác cho movie từ request
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setSlug(slugify.slugify(request.getTitle()));
        movie.setPoster(request.getPoster());
        movie.setStatus(status);
        movie.setActors(actors);
        movie.setGenres(genres);
        movie.setDirectors(directors);
        movie.setPublishedAt(publishAt);
        movie.setUpdatedAt(new Date());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setType(request.getType());

        return movieRepository.save(movie);

    }

    public void deleteMovie(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim với id = " + id));
        List<Review> reviewList = reviewRepository.findReviewByMovie_Id(id);
        reviewRepository.deleteAll(reviewList);
        movieRepository.delete(movie);
    }
}
