package com.example.movie_app.repository;

import com.example.movie_app.entity.Movie;
import com.example.movie_app.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findReviewByMovie_IdOrderByCreatedAtDesc(Integer id);

    List<Review> findReviewByMovie_Id(Integer id);
}
