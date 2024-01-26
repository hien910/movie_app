package com.example.movie_app;

import com.example.movie_app.entity.Blog;
import com.example.movie_app.entity.Movie;
import com.example.movie_app.entity.User;
import com.example.movie_app.model.enums.MovieType;
import com.example.movie_app.model.enums.UserRole;
import com.example.movie_app.repository.BlogRepository;
import com.example.movie_app.repository.MovieRepository;
import com.example.movie_app.repository.UserRepository;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
class MovieAppApplicationTests {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Test
    void save_users() {
        Faker faker = new Faker();


        for (int i = 0; i < 30; i++) {
            Date createdAt = faker.date().birthday();
            String name = faker.funnyName().name();
            User user = User.builder()
                    .name(name)
                    .email(faker.internet().emailAddress())
                    .password(faker.internet().password())
                    .avatar(generateLinkImage(name))
                    .role(faker.options().option(UserRole.class))
                    .createdAt(createdAt)
                    .updatedAt(createdAt)
                    .build();
            userRepository.save(user);
        }
    }


    @Test
    void save_blog() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();


        List<User> userList = userRepository.findUserByRole(UserRole.ADMIN);

        for (int i = 0; i < 30; i++) {

            boolean status = faker.bool().bool();
            Date createdAt = faker.date().birthday();
            Date publishedAt = null;
            if (status) {
                publishedAt = createdAt;
            }

            int randomIndex = random.nextInt(userList.size());

            String title = faker.book().title();
            Blog blog = Blog.builder()
                    .title(title)
                    .slug(slugify.slugify(title))
                    .description(faker.lorem().paragraph())
                    .content(faker.lorem().paragraph(50))
                    .thumbnail(faker.company().logo())
                    .status(faker.bool().bool())
                    .createdAt(createdAt)
                    .updatedAt(createdAt)
                    .publishedAt(publishedAt)
                    .user(userList.get(randomIndex))
                    .build();
            blogRepository.save(blog);
        }
    }

    @Test
    void save_movies() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();

        for (int i = 0; i < 200; i++) {
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
                    .poster(generateLinkImage(title))
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
    public static String generateLinkImage(String str) {
        return "https://placehold.co/200x200?text=" + str.substring(0, 1).toUpperCase();
    }

//    @Test
//    void test_movie_repo() {
////        List<Movie> movies = movieRepository.findAll();
////        System.out.println(movies);
//
////        Movie movie = movieRepository.findById(2).orElse(null);
//        List<Movie> movies = movieRepository.findByTypeOrderByPublishAtDesc(MovieType.PHIM_LE);
//        for (Movie movie:movies) {
//            System.out.println(movie);
//        }


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
//    }
}