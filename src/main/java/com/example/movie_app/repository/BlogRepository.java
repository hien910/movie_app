package com.example.movie_app.repository;

import com.example.movie_app.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findBlogByStatusOrderByPublishedAtDesc(Boolean status);

    Page<Blog> findBlogByStatusOrderByPublishedAtDesc(Boolean status, Pageable pageable);

    Blog findBlogByIdAndSlugAndStatus(Integer id, String slug, Boolean status);




}
