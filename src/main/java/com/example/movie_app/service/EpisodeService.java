package com.example.movie_app.service;

import com.example.movie_app.entity.Episode;
import com.example.movie_app.entity.Movie;
import com.example.movie_app.entity.Video;
import com.example.movie_app.exception.ResourceNotFoundException;
import com.example.movie_app.model.request.UpsertEpisodeRequest;
import com.example.movie_app.repository.EpisodeRepository;
import com.example.movie_app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final MovieRepository movieRepository;
    private final VideoService videoService;

    // Lấy danh sách tập phim của movie sắp xếp theo displayOrder tăng dần
    public List<Episode> getEpisodeListOfMovie(Integer movieId) {
        return episodeRepository.findByMovie_IdOrderByDisplayOrderAsc(movieId);
    }

    public List<Episode> getEpisodeListOfMovie(Integer movieId, Boolean status) {
        return episodeRepository.findByMovie_IdAndStatusOrderByDisplayOrderAsc(movieId, status);
    }

    public void uploadVideo(Integer id, MultipartFile file) {
        log.info("Uploading video for episode with id = {}", id);
        log.info("File name: {}", file.getOriginalFilename());

        // Kiểm tra tập phim có tồn tại không
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tập phim có id = " + id));

        // Upload video
        Video video = videoService.uploadVideo(file);

        episode.setVideoUrl(video.getUrl());
        episode.setDuration(video.getDuration());
        episodeRepository.save(episode);
    }

    public Episode getEpisode(Integer movieId, String tap, boolean episodeStatus) {
        if(tap.equals("Full")) {
            return episodeRepository.findByMovie_IdAndDisplayOrderAndStatus(movieId, 1, episodeStatus).orElse(null);
        } else {
            return episodeRepository.findByMovie_IdAndDisplayOrderAndStatus(movieId, Integer.parseInt(tap), episodeStatus).orElse(null);
        }
    }

    public void updateVideo(Integer id, UpsertEpisodeRequest request ) {

        // Kiểm tra tập phim có tồn tại không
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tập phim có id = " + id));

        episode.setTitle(request.getTitle());
        episode.setDisplayOrder(request.getDisplayOrder());
        episode.setStatus(request.getStatus());
        episode.setUpdatedAt(new Date());
        episode.setMovie(request.getMovie());
        episodeRepository.save(episode);
    }

    public void deleteEpisode(Integer id) {
        // Kiểm tra tập phim có tồn tại không
        Episode episode = episodeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tập phim có id = " + id));
        episodeRepository.deleteById(episode.getId());
    }

    public Episode createEpisode(UpsertEpisodeRequest request) {
        Movie movie = movieRepository.findById(request.getMovie().getId())
                .orElseThrow(() -> new ResourceNotFoundException("không thể tìm thấy phim trên"));
        Episode episode = Episode.builder()
                .title(request.getTitle())
                .movie(movie)
                .status(request.getStatus())
                .displayOrder(request.getDisplayOrder())
                .duration(1000)
                .build();
        return episodeRepository.save(episode);
    }

    public List<Episode> getEpisodeOfMovie(Integer id,Boolean status){
        return episodeRepository.findByMovie_IdAndStatusOrderByDisplayOrderAsc(id,status);
    }
}

