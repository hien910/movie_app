package com.example.movie_app.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MovieType {
    PHIM_LE("Phim lẻ"),
    PHIM_BO("Phim bộ"),
    PHIM_CHIEU_RAP("Phim chiếu rạp");

    private final String value;
}
