package com.example.movie_app.model.request;

import com.example.movie_app.model.enums.MovieType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertMovieRequest {
    String title;
    String description;
    Boolean status;
    MovieType type;
    Integer releaseYear;
    List<Integer> directorIds;
    List<Integer> actorIds;
    List<Integer> genreIds;
    String poster;
}
