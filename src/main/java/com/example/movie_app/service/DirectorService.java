package com.example.movie_app.service;

import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Director;
import com.example.movie_app.exception.ResourceNotFoundException;
import com.example.movie_app.model.request.UpsertActorRequest;

import com.example.movie_app.model.request.UpsertDirectorRequest;
import com.example.movie_app.repository.DirectorRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;
    public Director createDirector(UpsertDirectorRequest request) {
        Faker faker = new Faker();
        String name = request.getName();

        // Kiểm tra xem tên đã tồn tại hay chưa
        Director existing = directorRepository.findByName(name);
        if (existing != null) {
            // Nếu tên đã tồn tại, bạn có thể thực hiện các hành động phù hợp ở đây,
            // như throw một Exception hoặc trả về null tùy theo yêu cầu của ứng dụng của bạn.
            // Ví dụ:
            throw new IllegalArgumentException("Director đã tồn tại");
        }
        Director director = Director.builder()
                .name(request.getName())
                .description(request.getDescription())
                .avatar(request.getAvatar())
                .birthday(faker.date().birthday())
                .updatedAt(new Date())
                .createdAt(new Date())
                .build();
        return directorRepository.save(director);
    }

    public Director updateDirector(UpsertDirectorRequest request, Integer id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đạo diễn với id = " + id));

        Director existingActor = directorRepository.findByNameAndIdNot(request.getName(), id);
        if (existingActor != null) {
            throw new IllegalArgumentException("Tên đạo diễn đã tồn tại");
        }
        director.setName(request.getName());
        director.setAvatar(request.getAvatar());
        director.setDescription(request.getDescription());

        director.setUpdatedAt(new Date());
        return directorRepository.save(director);
    }

    public void deleteDirector(Integer id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đạo diễn với id = " + id));
        directorRepository.delete(director);
    }


}
