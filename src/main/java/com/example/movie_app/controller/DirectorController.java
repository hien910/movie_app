package com.example.movie_app.controller;

import com.example.movie_app.repository.ActorRepository;
import com.example.movie_app.repository.DirectorRepository;
import com.example.movie_app.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/directors")
public class DirectorController {
    private final DirectorRepository directorRepository;
    private final ImageService imageService;

    @GetMapping()
    public String viewActorPage(Model model) {
        model.addAttribute("directors", directorRepository.findAllByOrderByCreatedAtDesc());
        model.addAttribute("images", imageService.getAllImagesByCurrentUser());
        return "admin/director/director";
    }
}
