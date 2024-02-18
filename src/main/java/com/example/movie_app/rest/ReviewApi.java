package com.example.movie_app.rest;

import com.example.movie_app.entity.Review;
import com.example.movie_app.model.request.UpsertReviewRequest;
import com.example.movie_app.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApi {
    private final ReviewService reviewService;

    // Tạo review - POST
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody UpsertReviewRequest request) {
        Review review = reviewService.createReview(request);
        return new ResponseEntity<>(review, HttpStatus.CREATED); // 201
    }

    // Cập nhật review - PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id, @RequestBody UpsertReviewRequest request) {
        Review review = reviewService.updateReview(id, request);
        return ResponseEntity.ok(review); // 200
//        try {
//            Review review = reviewService.updateReview(id, request);
//            return ResponseEntity.ok(review); // 200
//        } catch (ResourceNotFoundException e) {
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
//            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // 404
//        } catch (BadRequestException e) {
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
//            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400
//        }
    }

    // Xóa review - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
