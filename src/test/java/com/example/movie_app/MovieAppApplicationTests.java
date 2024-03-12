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
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
        for (int i = 0; i < 15; i++) {
            String name = faker.funnyName().name();
            Genre genre = Genre.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .build();
            genreRepository.save(genre);
        }
    }

    @Test
    void save_actors() {
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            String name = faker.name().fullName();
            Actor actor = Actor.builder()
                    .name(name)
                    .description(faker.lorem().paragraph())
                    .avatar(generateLinkImage(name))
                    .birthday(faker.date().birthday())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            actorRepository.save(actor);
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
    void save_users() {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String name = faker.name().fullName();
            User user = User.builder()
                    .name(name)
                    .password("123")
                    .email(faker.internet().emailAddress())
                    .avatar(generateLinkImage(name))
                    .role(i == 0 || i == 1 ? UserRole.ADMIN : UserRole.USER)
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    void save_blogs() {
        Slugify slugify = Slugify.builder().build();
        Faker faker = new Faker();
        Random rd = new Random();

        List<User> userList = userRepository.findByRole(UserRole.ADMIN);

        Date start = new Calendar.Builder().setDate(2023, 8, 1).build().getTime();
        Date end = new Date();

        for (int i = 0; i < 20; i++) {
            String title = faker.book().title();
            boolean status = faker.bool().bool();
            Date createdAt = randomDateBetweenTwoDates(start, end);
            Date publishedAt = null;
            if (status) {
                publishedAt = createdAt;
            }

            Blog blog = Blog.builder()
                    .title(title)
                    .slug(slugify.slugify(title))
                    .description(faker.lorem().paragraph())
                    .content(faker.lorem().paragraph(100))
                    .status(rd.nextInt(2) == 0)
                    .user(userList.get(rd.nextInt(userList.size())))
                    .thumbnail(generateLinkImage(title))
                    .createdAt(createdAt)
                    .updatedAt(createdAt)
                    .publishedAt(publishedAt)
                    .build();
            blogRepository.save(blog);
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
    void save_movies() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();

        Date start = new Calendar.Builder().setDate(2023, 8, 1).build().getTime();
        Date end = new Date();

        List<Genre> genreList = genreRepository.findAll();
        List<Actor> actorList = actorRepository.findAll();
        List<Director> directorList = directorRepository.findAll();

        for (int i = 0; i < 100; i++) {
            // Lấy ngẫu nhiên danh sách 1 -> 3 thể loại
            List<Genre> rdGenreList = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3) + 1; j++) {
                Genre genre = genreList.get(random.nextInt(genreList.size()));
                if (!rdGenreList.contains(genre)) {
                    rdGenreList.add(genre);
                }
            }

            // Lấy ngẫu nhiên danh sách 5 -> 7 diễn viên
            List<Actor> rdActorList = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3) + 5; j++) {
                Actor actor = actorList.get(random.nextInt(actorList.size()));
                if (!rdActorList.contains(actor)) {
                    rdActorList.add(actor);
                }
            }

            // Lấy ngẫu nhiên 1 -> 3 đạo diễn
            List<Director> rdDirectorList = new ArrayList<>();

            for (int j = 0; j < random.nextInt(3) + 1; j++) {
                Director director = directorList.get(random.nextInt(directorList.size()));
                if (!rdDirectorList.contains(director)) {
                    rdDirectorList.add(director);
                }
            }

            String title = faker.book().title();
            boolean status = faker.bool().bool();
            Date createdAt = randomDateBetweenTwoDates(start, end);
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
                    .genres(rdGenreList)
                    .actors(rdActorList)
                    .directors(rdDirectorList)
                    .build();

            movieRepository.save(movie);
        }
    }

    @Test
    void save_episodes() {
        // Lấy danh sách phim
        List<Movie> movieList = movieRepository.findAll();
        Random random = new Random();

        // Duyệt qua từng phim -> Kiểm tra type phim
        for (Movie movie: movieList) {
            if(movie.getType().getValue().equals("Phim bộ")){
                for (int i = 0; i < random.nextInt(5)+5; i++) {
                    Episode episode = Episode.builder()
                            .title("Tập " + (i+1) )
                            .displayOrder(i+1)
                            .status(true)
                            .createdAt(new Date())
                            .updatedAt(new Date())
                            .publishedAt(new Date())
                            .movie(movie)
                            .build();
                    episodeRepository.save(episode);
                }
            }else {
                Episode episode = Episode.builder()
                        .title("Full")
                        .displayOrder(1)
                        .status(true)
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .publishedAt(new Date())
                        .movie(movie)
                        .build();
                episodeRepository.save(episode);
            }
        }
        // Nếu type = PHIM_BO -> Tạo ra 5 -> 10 tập phim tương ứng
        // Nếu type = PHIM_LE hoặc PHIM_CHIEU_RAP -> Tạo ra 1 tập phim tương ứng
        // Chưa cần thông tin về videoUrl và duration (set null)
    }

    @Test
    void save_reviews() {
        Faker faker = new Faker();
        Random random = new Random();

        List<User> userList = userRepository.findAll();
        List<Movie> movieList = movieRepository.findAll();

        for(Movie movie : movieList) {
            // Mỗi phim sẽ có 1 số lượng review ngẫu nhiên từ 10 -> 20 review
            int numberOfReview = random.nextInt(10) + 10;
            for (int i = 0; i < numberOfReview; i++) {
                Review review = Review.builder()
                        .comment(faker.lorem().paragraph())
                        .rating(faker.number().numberBetween(1, 10))
                        .user(userList.get(random.nextInt(userList.size())))
                        .movie(movie)
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build();

                reviewRepository.save(review);
            }
        }
    }

    // write method to random date between 2 date
    private Date randomDateBetweenTwoDates(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);
        return new Date(randomMillisSinceEpoch);
    }

    // generate link author avatar follow struct : https://placehold.co/200x200?text=[...]
    public static String generateLinkImage(String str) {
        return "https://placehold.co/200x200?text=" + str.substring(0, 1).toUpperCase();
    }
    @Test
    void update_password() {
        List<User> userList = userRepository.findAll();
        userList.forEach(user -> {
            user.setPassword(passwordEncoder.encode("123"));
            userRepository.save(user);
        });
    }
}