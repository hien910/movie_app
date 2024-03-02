package com.example.movie_app;

import com.example.movie_app.entity.*;
import com.example.movie_app.model.enums.MovieType;
import com.example.movie_app.model.enums.UserRole;
import com.example.movie_app.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void save_genres() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        String name = faker.book().genre();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Date createdAt = faker.date().birthday();
            Genre genre = Genre.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .createdAt(createdAt)
                    .updatedAt(createdAt)
                    .build();
            genreRepository.save(genre);
        }
    }

    @Test
    void save_directors() {
        Faker faker = new Faker();
        for (int i = 0; i < 30; i++) {
            String name = faker.name().fullName();
            Director director = Director.builder()
                    .name(name)
                    .description(faker.lorem().paragraph())
                    .avatar(generateLinkImage(name))
                    .birthday(faker.date().birthday())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            directorRepository.save(director);
        }
    }

    @Test
    void save_comments() {
        Faker faker = new Faker();
        Random random = new Random();

        List<User> userList = userRepository.findAll();
        List<Blog> blogList = blogRepository.findAll();

        for (Blog blog : blogList) {
            // Mỗi blog sẽ có 1 số lượng comment ngẫu nhiên từ 5 -> 10 comment
            int numberOfComment = random.nextInt(5) + 5;
            for (int i = 0; i < numberOfComment; i++) {
                Comment comment = Comment.builder()
                        .content(faker.lorem().paragraph())
                        .user(userList.get(random.nextInt(userList.size())))
                        .blog(blog)
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build();

                commentRepository.save(comment);
            }
        }
    }


    @Test
    void save_reviews() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();
        Date startDate = new Date(122, 0, 1);
        Date endDate = new Date(124, 0, 1);

        List<Movie> movieList = movieRepository.findMoviesByStatus(Boolean.TRUE);
        List<User> userList = userRepository.findAll();
        for (Movie movie : movieList) {
            int numOfReview = random.nextInt(10) + 10;


            for (int i = 0; i < numOfReview; i++) {

                Date createdAt = faker.date().between(startDate, endDate);
                Review review = Review.builder()
                        .comment(faker.lorem().paragraph(50))
                        .rating(faker.number().numberBetween(1, 10))
                        .user(userList.get(random.nextInt(userList.size())))
                        .movie(movie)
                        .createdAt(createdAt)
                        .updatedAt(createdAt)
                        .build();
                reviewRepository.save(review);

            }
        }
    }


    @Test
    void save_actors() {
        Faker faker = new Faker();

        for (int i = 0; i < 30; i++) {
            Actor actor = Actor.builder()
                    .name(faker.leagueOfLegends().champion())
                    .description(faker.lorem().paragraph(50))
                    .build();
            actorRepository.save(actor);
        }

    }

    @Test
    void save_users() {
        Faker faker = new Faker();


        for (int i = 0; i < 30; i++) {
            Date createdAt = faker.date().birthday();
            String name = faker.leagueOfLegends().champion();
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
    @Test
    void update_password() {
        List<User> userList = userRepository.findAll();
        userList.forEach(user -> {
            user.setPassword(passwordEncoder.encode("123"));
            userRepository.save(user);
        });
    }
}