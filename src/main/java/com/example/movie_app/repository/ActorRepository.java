package com.example.movie_app.repository;


import com.example.movie_app.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

    List<Actor> findAllByOrderByCreatedAtDesc();
}
