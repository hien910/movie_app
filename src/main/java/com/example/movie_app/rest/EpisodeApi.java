package com.example.movie_app.rest;

import com.example.movie_app.entity.Episode;
import com.example.movie_app.entity.Movie;
import com.example.movie_app.model.request.UpsertEpisodeRequest;
import com.example.movie_app.model.request.UpsertMovieRequest;
import com.example.movie_app.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/episodes")
@RequiredArgsConstructor
public class EpisodeApi {
    private final EpisodeService episodeService;
    @PostMapping
    public ResponseEntity<?> createEpisode(@RequestBody UpsertEpisodeRequest request){
        Episode episode = episodeService.createEpisode(request);
        return new ResponseEntity<>(episode, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/upload-video")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {
        episodeService.uploadVideo(id, file);
        return ResponseEntity.ok().build(); // status code 200
    }
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateVideo( @PathVariable Integer id, @RequestBody UpsertEpisodeRequest request){
        episodeService.updateVideo(id, request);
        return ResponseEntity.ok().build(); // status code 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEpisode(@PathVariable Integer id) {
        episodeService.deleteEpisode(id);
        return ResponseEntity.noContent().build(); // status code 204
    }
}
