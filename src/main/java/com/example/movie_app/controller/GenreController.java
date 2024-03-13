package com.example.movie_app.controller;

import com.example.movie_app.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/genres")
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping()
    public String viewGenrePage(Model model) {
        model.addAttribute("genres", genreRepository.findAllByOrderByCreatedAtDesc());
        return "admin/genre/genre";
    }
}
