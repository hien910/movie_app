package com.example.movie_app.controller;

import com.example.movie_app.entity.*;
import com.example.movie_app.model.enums.MovieType;
import com.example.movie_app.repository.ActorRepository;
import com.example.movie_app.repository.DirectorRepository;
import com.example.movie_app.repository.EpisodeRepository;
import com.example.movie_app.repository.GenreRepository;
import com.example.movie_app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/movies")
public class MovieController {
    private final MovieService movieService;
    private final ImageService imageService;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final EpisodeService episodeService;

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

    public String viewDetailPage(@PathVariable Integer id, Model model) {
        Movie movie = movieService.getMovieById(id);
        List<Episode> episodes = episodeService.getEpisodeListOfMovie(id);
        List<Actor> actorList = actorRepository.findAll();
        List<Director> directorList = directorRepository.findAll();
        List<Genre> genreList = genreRepository.findAll();

        model.addAttribute("movie", movie);
        model.addAttribute("images", imageService.getAllImagesByCurrentUser());

        model.addAttribute("actorList", actorList);
        model.addAttribute("directorList", directorList);
        model.addAttribute("genreList", genreList);
        model.addAttribute("episodes", episodes);
        return "admin/movie/detail";
    }

}