package com.example.movie_app;

import com.example.movie_app.entity.Movie;
import com.example.movie_app.model.enums.MovieType;
import com.example.movie_app.repository.MovieRepository;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
class MovieAppApplicationTests {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    void save_movies() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            String title = faker.book().title();
            boolean status = faker.bool().bool();
            Date createdAt = faker.date().birthday();
            Date publishedAt = null;
            if (status) {
                publishedAt = createdAt;
            }

            Movie movie = Movie.builder()
                    .title(title)
                    .slug(slugify.slugify(title))
                    .description(faker.lorem().paragraph())
                    .poster(faker.company().logo())
                    .releaseYear(faker.number().numberBetween(2021, 2024))
                    .view(faker.number().numberBetween(1000, 10000))
                    .rating(faker.number().randomDouble(1, 6, 10))
                    .type(MovieType.values()[random.nextInt(MovieType.values().length)])
                    .status(status)
                    .createdAt(createdAt)
                    .updatedAt(createdAt)
                    .publishedAt(publishedAt)
                    .build();

            movieRepository.save(movie);
        }
    }

    @Test
    void test_movie_repo() {
//        List<Movie> movies = movieRepository.findAll();
//        System.out.println(movies);

//        Movie movie = movieRepository.findById(2).orElse(null);
        List<Movie> movies = movieRepository.findByTypeOrderByPublishAtDesc(MovieType.PHIM_LE);
        for (Movie movie:movies) {
            System.out.println(movie);
        }


//        movie.setTitle("Avatar 2");
//        movieRepository.save(movie);

//        movieRepository.deleteById(1);
//
//        Movie movie = movieRepository.findById(2).orElse(null);
//        movieRepository.delete(movie);

//        List<Movie> movies = movieRepository.findAll(Sort.by("view").descending());
//        movies.forEach(movie -> System.out.println(movie.getView()));

//        Pageable pageable = PageRequest.of(0, 6, Sort.by("publishedAt").descending());
//        Page<Movie> pageData = movieRepository.findByTypeAndStatus(MovieType.PHIM_LE, true, pageable);
//        System.out.println(pageData.getContent());
//        System.out.println(pageData.getTotalPages());
//        System.out.println(pageData.getTotalElements());
//        pageData.getContent().forEach(System.out::println);
    }
}