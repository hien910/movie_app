package com.example.movie_app.controller;


import com.example.movie_app.entity.*;
import com.example.movie_app.model.enums.MovieType;

import com.example.movie_app.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final MovieService movieService;
    private final UserService userService;
    private final BlogService blogService;
    private final ReviewService reviewService;
    private final HttpSession httpSession;
    private final EpisodeService episodeService;

    //
    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("moviesHot", movieService.getHotMovies(true, 1,8));
        model.addAttribute("moviesPL", movieService.findByTypeOrderByPublishAtDesc(MovieType.PHIM_LE));
        model.addAttribute("moviesPB", movieService.findByTypeOrderByPublishAtDesc(MovieType.PHIM_BO));
        model.addAttribute("moviesPCR", movieService.findByTypeOrderByPublishAtDesc(MovieType.PHIM_CHIEU_RAP));

        model.addAttribute("blogs", blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,3 ));
        return "web/index";
    }

    //
    // Danh sách phim chiếu rạp
    @GetMapping("/phim-chieu-rap")
    public String getPhimChieuRap(Model model,
                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false, defaultValue = "12") Integer size) {
        Page<Movie> pageData = movieService.getMoviesByType(MovieType.PHIM_CHIEU_RAP, true, page, size);
        model.addAttribute("moviesRelated", movieService.findMovieRelated(MovieType.PHIM_CHIEU_RAP));
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);

        model.addAttribute("blogs", blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,3 ));
        return "web/phim-chieu-rap";
    }

    // Danh sách phim lẻ
    @GetMapping("/phim-le")
    public String getPhimLe(Model model,
                            @RequestParam(required = false, defaultValue = "1") Integer page,
                            @RequestParam(required = false, defaultValue = "12") Integer size) {
        Page<Movie> pageData = movieService.getMoviesByType(MovieType.PHIM_LE, true, page, size);
        model.addAttribute("moviesRelated", movieService.findMovieRelated(MovieType.PHIM_LE));
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);

        model.addAttribute("blogs", blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,3 ));
        return "web/phim-le";
    }

    // Danh sách phim bộ
    @GetMapping("/phim-bo")
    public String getPhimBo(Model model,
                            @RequestParam(required = false, defaultValue = "1") Integer page,
                            @RequestParam(required = false, defaultValue = "12") Integer size) {
        Page<Movie> pageData = movieService.getMoviesByType(MovieType.PHIM_BO, true, page, size);
        model.addAttribute("moviesRelated", movieService.findMovieRelated(MovieType.PHIM_BO));
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);

        model.addAttribute("blogs", blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,3 ));
        return "web/phim-bo";
    }

    //     Chi tiết phim
    @GetMapping("/phim/{id}/{slug}")
    public String getChiTietPhim(@PathVariable Integer id, @PathVariable String slug, Model model) {
        Movie movie = movieService.getMovie(id, slug, true);
        List<Review> reviewList = reviewService.getReviewsByMovie(id);

        model.addAttribute("movie", movie);
        model.addAttribute("blogs", blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,3 ));
        model.addAttribute("moviesRelated", movieService.findMovieRelated(MovieType.valueOf(String.valueOf(movie.getType()))));
        List<Review> reviews = reviewService.getReviewsByMovie(id);
        model.addAttribute("reviews", reviews);

        return "web/chi-tiet-phim";
    }
    // Xem phim
    @GetMapping("/xem-phim/{id}/{slug}")
    public String getXemPhimPage(Model model, @PathVariable Integer id, @PathVariable String slug, @RequestParam(required = false) String tap) {
        Movie movie = movieService.getMovie(id, slug, true);
        List<Movie> relatedMovieList = movieService.findMovieRelated(MovieType.valueOf(String.valueOf(movie.getType())));
        List<Review> reviewList = reviewService.getReviewsByMovie(id);
        List<Episode> episodes = episodeService.getEpisodeListOfMovie(id, true);
        Episode currentEpisode = episodeService.getEpisode(id, tap, true);

        model.addAttribute("movie", movie);
        model.addAttribute("relatedMovieList", relatedMovieList);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("episodes", episodes);
        model.addAttribute("currentEpisode", currentEpisode);
        return "web/xem-phim";
    }

    @GetMapping("/danh-sach-blog")
    public String getListBlog(Model model ,
                              @RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "8") Integer size) {
        Page<Blog> pageData = blogService.getBlogByStatus(true, page, size);

        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        model.addAttribute("moviesHot", movieService.getHotMovies(true, 1,6));
        return "web/list-blog";
    }
    @GetMapping("/blog/{id}/{slug}")
    public String getDetailBlog(@PathVariable Integer id, @PathVariable String slug, Model model) {
        Blog blog = blogService.findBlogByIdAndSlug(id, slug, true);
        model.addAttribute("blog", blog);
        model.addAttribute("blogs", blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,3 ));
        model.addAttribute("moviesHot", movieService.getHotMovies(true, 1,6));

        return "web/detail-blog";
    }
    @GetMapping("/dang-ky")
    public String getDangKyPage() {
        User user = (User) httpSession.getAttribute("currentUser");
        if (user != null) {
            return "redirect:/";
        }
        return "web/auth/dang-ky";
    }

    @GetMapping("/dang-nhap")
    public String getDangNhapPage() {
        User user = (User) httpSession.getAttribute("currentUser");
        if (user != null) {
            return "redirect:/";
        }
        return "web/auth/dang-nhap";
    }
}
