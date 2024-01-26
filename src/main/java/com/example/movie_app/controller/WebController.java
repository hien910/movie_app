package com.example.movie_app.controller;


import com.example.movie_app.entity.Blog;
import com.example.movie_app.entity.Movie;
import com.example.movie_app.entity.User;
import com.example.movie_app.model.enums.MovieType;

import com.example.movie_app.service.BlogService;
import com.example.movie_app.service.MovieService;
import com.example.movie_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class WebController {

    @Autowired
    private final MovieService movieService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final BlogService blogService;





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
        model.addAttribute("blogs", blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,3 ));
        model.addAttribute("moviesRelated", movieService.findMovieRelated(MovieType.valueOf(String.valueOf(movie.getType()))));
        return "web/chi-tiet-phim";
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
