package com.example.movie_app.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(columnDefinition ="TEXT")
    String comment;

    Integer rating;

    @Transient
    String ratingText;


    Date createdAt;
    Date updatedAt;
    public String getRatingText() {
        if (rating == null) {
            return "Chưa có đánh giá";
        }

        // switch rating from 1 to 10
        return switch (rating) {
            case 1 -> "Tệ";
            case 2 -> "Kém";
            case 3 -> "Trung bình";
            case 4 -> "Tạm được";
            case 5 -> "Hay";
            case 6 -> "Rất hay";
            case 7 -> "Tuyệt vời";
            case 8 -> "Tuyệt hảo";
            case 9 -> "Xuất sắc";
            case 10 -> "Quá tuyệt vời";
            default -> "Chưa có đánh giá";
        };
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    @PrePersist
    void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = new Date();
    }
}
