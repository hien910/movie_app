package com.example.movie_app.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    ADMIN("ROLE_ADIMN"),
    USER("ROLE_USER");
    private final String value;

}
