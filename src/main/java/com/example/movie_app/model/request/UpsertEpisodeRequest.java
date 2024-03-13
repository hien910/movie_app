package com.example.movie_app.model.request;

import com.example.movie_app.entity.Movie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertEpisodeRequest {
    String title;
    Integer displayOrder;
    Boolean status;
    Date createdAt;
    Date updatedAt;
    Date publishedAt;
    Movie movie;
}
