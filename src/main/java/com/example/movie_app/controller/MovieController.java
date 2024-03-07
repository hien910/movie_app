package com.example.movie_app.controller;

import com.example.movie_app.entity.Blog;
import com.example.movie_app.entity.Image;
import com.example.movie_app.entity.Movie;
import com.example.movie_app.model.enums.MovieType;
import com.example.movie_app.repository.ActorRepository;
import com.example.movie_app.repository.DirectorRepository;
import com.example.movie_app.repository.GenreRepository;
import com.example.movie_app.service.BlogService;
import com.example.movie_app.service.ImageService;
import com.example.movie_app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/movies")
public class MovieController {
    private final MovieService movieService;
    private final ImageService imageService;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    @GetMapping()
    public String viewHomePage(Model model){
        model.addAttribute("movies", movieService.findAll());
        return "admin/movie/index";
    }

    @GetMapping("/create")
    public String viewCreatePage(Model model) {
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("movieTypes", MovieType.values());
        return "admin/movie/create";
    }

    @GetMapping("/{id}/detail-movie")

    public String viewDetailPage(@PathVariable Integer id, Model model,
                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "12") Integer size) {
        Movie movie = movieService.getMovieById(id);

        Page<Image> pageData = imageService.getAllImagesByCurrentUser(page, size);

        model.addAttribute("movie", movie);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        model.addAttribute("images", pageData.getContent());
        return "admin/movie/detail";
    }
}