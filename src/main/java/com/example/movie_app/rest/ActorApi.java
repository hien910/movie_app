package com.example.movie_app.rest;

import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.model.request.UpsertActorRequest;
import com.example.movie_app.model.request.UpsertGenreRequest;
import com.example.movie_app.service.ActorService;
import com.example.movie_app.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/actors")
@RequiredArgsConstructor
public class ActorApi {
    private final ActorService actorService;

    @PostMapping
    public ResponseEntity<?> creatGenre(@RequestBody UpsertActorRequest request){
        Actor actor = actorService.creatActor(request);
        return ResponseEntity.ok(actor);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateGenre(@RequestBody UpsertActorRequest request,@PathVariable Integer id){
        Actor actor = actorService.updateActor(request, id);
        return ResponseEntity.ok(actor);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteGenre(@PathVariable Integer id){
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }
}