package com.example.movie_app.controller;

import com.example.movie_app.repository.ActorRepository;
import com.example.movie_app.repository.GenreRepository;
import com.example.movie_app.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/actors")
public class ActorController {
    private final ActorRepository actorRepository;
    private final ImageService imageService;

    @GetMapping()
    public String viewActorPage(Model model) {
        model.addAttribute("actors", actorRepository.findAllByOrderByCreatedAtDesc());
        model.addAttribute("images", imageService.getAllImagesByCurrentUser());
        return "admin/actor/actor";
    }
}
