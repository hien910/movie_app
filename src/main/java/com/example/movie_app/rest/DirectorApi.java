package com.example.movie_app.rest;

import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Director;
import com.example.movie_app.model.request.UpsertActorRequest;
import com.example.movie_app.model.request.UpsertDirectorRequest;
import com.example.movie_app.service.ActorService;
import com.example.movie_app.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/directors")
@RequiredArgsConstructor
public class DirectorApi {
    private final DirectorService directorService;

    @PostMapping
    public ResponseEntity<?> creatGenre(@RequestBody UpsertDirectorRequest request){
        Director director = directorService.createDirector(request);
        return ResponseEntity.ok(director);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateGenre(@RequestBody UpsertDirectorRequest request, @PathVariable Integer id){
        Director director =directorService.updateDirector(request, id);
        return ResponseEntity.ok(director);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteGenre(@PathVariable Integer id){
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }
}