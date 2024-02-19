package com.example.movie_app.repository;

import com.example.movie_app.entity.Comment;
import com.example.movie_app.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}