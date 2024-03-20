package com.example.movie_app.service;

import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Director;
import com.example.movie_app.exception.ResourceNotFoundException;
import com.example.movie_app.model.request.UpsertActorRequest;

import com.example.movie_app.model.request.UpsertDirectorRequest;
import com.example.movie_app.repository.DirectorRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;
    public static String generateLinkImage(String str) {
        return "https://placehold.co/200x200?text=" + str.substring(0, 1).toUpperCase();
    }
    public Director creatDirector(UpsertActorRequest request) {
        Faker faker = new Faker();
        Director director = Director.builder()
                .name(request.getName())
                .description(request.getDescription())
                .avatar(generateLinkImage("U"))
                .birthday(request.getBirthday())
                .updatedAt(new Date())
                .createdAt(new Date())
                .build();
        return directorRepository.save(director);
    }

    public Director updateActor(UpsertDirectorRequest request, Integer id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + id));
        director.setName(request.getName());
        director.setAvatar(request.getAvatar());
        director.setDescription(request.getDescription());
        director.setBirthday(request.getBirthday());
        director.setUpdatedAt(new Date());
        return directorRepository.save(director);
    }

    public void deleteActor(Integer id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + id));
        directorRepository.delete(director);
    }
}
