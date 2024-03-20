package com.example.movie_app.service;

import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.exception.ResourceNotFoundException;
import com.example.movie_app.model.request.UpsertActorRequest;
import com.example.movie_app.model.request.UpsertGenreRequest;
import com.example.movie_app.repository.ActorRepository;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;
    public static String generateLinkImage(String str) {
        return "https://placehold.co/200x200?text=" + str.substring(0, 1).toUpperCase();
    }
    public Actor creatActor(UpsertActorRequest request) {
        Faker faker = new Faker();
        Actor actor = Actor.builder()
                .name(request.getName())
                .description(request.getDescription())
                .avatar(generateLinkImage("U"))
                .birthday(request.getBirthday())
                .updatedAt(new Date())
                .createdAt(new Date())
                .build();
        return actorRepository.save(actor);
    }

    public Actor updateActor(UpsertActorRequest request, Integer id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + id));
        actor.setName(request.getName());
        actor.setAvatar(request.getAvatar());
        actor.setDescription(request.getDescription());
        actor.setBirthday(request.getBirthday());
        actor.setUpdatedAt(new Date());
        return actorRepository.save(actor);
    }

    public void deleteActor(Integer id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + id));
        actorRepository.delete(actor);
    }
}
