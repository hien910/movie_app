package com.example.movie_app.rest;

import com.example.movie_app.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/admin/images")
@RequiredArgsConstructor
public class ImageApi {
    private final ImageService imageService;
    // 1. Lấy danh sách ảnh của user
    @GetMapping
    public ResponseEntity<?> getAllImagesByCurrentUser() {
        return ResponseEntity.ok(imageService.getAllImagesByCurrentUser());
    }

    // 2. Upload ảnh mới
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(imageService.uploadImage(file), HttpStatus.CREATED);
    }

    // 3. Xóa ảnh
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
