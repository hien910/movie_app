package com.example.movie_app.repository;

import com.example.movie_app.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> findByUser_IdOrderByCreatedAtDesc(Integer id);
    Page<Image> findByUser_IdOrderByCreatedAt(Integer id, Pageable pageable);
}