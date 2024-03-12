package com.example.movie_app.service;

import com.example.movie_app.entity.User;
import com.example.movie_app.entity.Video;
import com.example.movie_app.exception.ResourceNotFoundException;
import com.example.movie_app.repository.VideoRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class VideoService {
    private static final String uploadDir = "video_uploads";
    public static final long CHUNK_SIZE = 100000L;
    private final HttpSession session;
    private final VideoRepository videoRepository;
    public VideoService(HttpSession session, VideoRepository videoRepository) {
        this.session = session;
        this.videoRepository = videoRepository;
        createDirectory(uploadDir);
    }

    public void createDirectory(String name) {
        Path path = Paths.get(name);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                log.error("Cannot create directory: " + path);
                log.error(e.getMessage());
                throw new RuntimeException("Cannot create directory: " + path);
            }
        }
    }

    public Video uploadVideo(MultipartFile file) {
        User user = (User) session.getAttribute("currentUser");

        // TODO: Validate file (kiểm tra tên file, kích thước file, định dạng file)

        // Upload file vào folder
        String videoId = UUID.randomUUID().toString();
        Path rootPath = Paths.get(uploadDir);
        Path filePath = rootPath.resolve(videoId);

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            log.error("Cannot upload file: " + filePath);
            log.error(e.getMessage());
            throw new RuntimeException("Cannot upload file: " + filePath);
        }

        // TODO: Lấy thông tin video (độ dài, kích thước, định dạng, ...)
        int duration = 1000; // Độ dài video (giây)

        // Lưu thông tin video vào database
        Video video = Video.builder()
                .id(videoId)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize() / 1048576.0) // Convert to MB
                .duration(duration)
                .url("/api/videos/" + videoId) // url để xem video: /api/videos/{id}
                .user(user)
                .build();
        return videoRepository.save(video);
    }

    public void deleteVideo(String id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));

        // Xóa ảnh trong folder
        Path filePath = Paths.get(uploadDir).resolve(video.getId());

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("Cannot delete file: " + filePath);
            log.error(e.getMessage());
            throw new RuntimeException("Cannot delete file: " + filePath);
        }

        // Xóa ảnh trong database
        videoRepository.deleteById(id);
    }

    public ResourceRegion getVideoResourceRegion(String fileName, long start, long end) throws IOException {
        UrlResource videoResource = new UrlResource("file:" + uploadDir + File.separator + fileName);

        if (!videoResource.exists() || !videoResource.isReadable()) {
            throw new IOException("Video not found");
        }

        Resource video = videoResource;
        long contentLength = video.contentLength();
        end = Math.min(end, contentLength - 1);

        long rangeLength = Math.min(CHUNK_SIZE, end - start + 1);
        return new ResourceRegion(video, start, rangeLength);
    }
}
