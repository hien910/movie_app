package com.example.movie_app.controller;


import com.example.movie_app.entity.Movie;
import com.example.movie_app.model.enums.MovieType;

import com.example.movie_app.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

@Controller
public class WebController {
    private final MovieService movieService;

    @Autowired
    public WebController(MovieService movieService) {
        this.movieService = movieService;
    }

    //
    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("moviesHot", movieService.findMovieHot());
        model.addAttribute("moviesPL", movieService.findByTypeOrderByPublishAtDesc(MovieType.PHIM_LE));
        model.addAttribute("moviesPB", movieService.findByTypeOrderByPublishAtDesc(MovieType.PHIM_BO));
        model.addAttribute("moviesPCR", movieService.findByTypeOrderByPublishAtDesc(MovieType.PHIM_CHIEU_RAP));
        return "web/index";
    }

    //
    // Danh sách phim chiếu rạp
    @GetMapping("/phim-chieu-rap")
    public String getPhimChieuRap(Model model) {
        model.addAttribute("moviesRelated", movieService.findMovieRelated(MovieType.PHIM_CHIEU_RAP));
        model.addAttribute("moviesPCR", movieService.findByTypeOrderByPublishAtDesc(MovieType.PHIM_CHIEU_RAP));
        return "web/phim-chieu-rap";
    }

    // Danh sách phim lẻ
    @GetMapping("/phim-le")
    public String getPhimLe(Model model) {
        model.addAttribute("moviesRelated", movieService.findMovieRelated(MovieType.PHIM_LE));
        model.addAttribute("moviesPL", movieService.findByTypeOrderByPublishAtDesc(MovieType.PHIM_LE));
        return "web/phim-le";
    }

    // Danh sách phim bộ
    @GetMapping("/phim-bo")
    public String getPhimBo(Model model) {
        model.addAttribute("moviesRelated", movieService.findMovieRelated(MovieType.PHIM_BO));
        model.addAttribute("moviesPB", movieService.findByTypeOrderByPublishAtDesc(MovieType.PHIM_BO));
        return "web/phim-bo";
    }

    //     Chi tiết phim
    @GetMapping("/phim/{id}/{slug}")
    public String getChiTietPhim(@PathVariable Integer id, @PathVariable String slug, Model model) {
        Movie movie = movieService.findMovieById(id);

        // Kiểm tra nếu không tìm thấy phim
        if (movie == null) {
            // Xử lý trường hợp không tìm thấy, có thể redirect hoặc hiển thị trang lỗi
            return "redirect:/error"; // hoặc "web/error-page"
        }
        String titleSlug = SlugUtils.createSlug(movie.getTitle());

        // Kiểm tra xem slug từ movie.title có khớp với slug truyền vào hay không
        if (!slug.equals(titleSlug)) {
            // Xử lý trường hợp slug không khớp, có thể redirect hoặc hiển thị trang lỗi
            return "redirect:/error"; // hoặc "web/error-page"
        }

        // Truyền thông tin phim đến view thông qua model
        model.addAttribute("movie", movie);
        model.addAttribute("moviesRelated", movieService.findMovieRelated(MovieType.valueOf(String.valueOf(movie.getType()))));
        return "web/chi-tiet-phim";
    }

    public static class SlugUtils {

        private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
        private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

        public static String createSlug(String input) {
            String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
            String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
            String slug = NON_LATIN.matcher(normalized).replaceAll("");
            return slug.toLowerCase(Locale.ENGLISH);
        }
    }
}
