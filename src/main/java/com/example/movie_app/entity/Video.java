package com.example.movie_app.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "videos")
public class Video {
    @Id
    String id;

    // Những thông tin cần thiết cho video thì bổ sung thêm
    String name;
    String type;
    Double size;
    Integer duration;
    String url;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    Date createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }
}
