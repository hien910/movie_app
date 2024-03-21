package com.example.movie_app.service;

import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.exception.ResourceNotFoundException;
import com.example.movie_app.model.request.UpsertActorRequest;
import com.example.movie_app.model.request.UpsertGenreRequest;
import com.example.movie_app.repository.ActorRepository;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;
    public Actor creatActor(UpsertActorRequest request) {
        Faker faker = new Faker();
        String name = request.getName();

        // Kiểm tra xem tên đã tồn tại hay chưa
        Actor existingActor = actorRepository.findByName(name);
        if (existingActor != null) {
            // Nếu tên đã tồn tại, bạn có thể thực hiện các hành động phù hợp ở đây,
            // như throw một Exception hoặc trả về null tùy theo yêu cầu của ứng dụng của bạn.
            // Ví dụ:
            throw new IllegalArgumentException("Tên Actor đã tồn tại");
        }
        Actor actor = Actor.builder()
                .name(request.getName())
                .description(request.getDescription())
                .avatar(request.getAvatar())
                .birthday(faker.date().birthday())
                .updatedAt(new Date())
                .createdAt(new Date())
                .build();
        return actorRepository.save(actor);
    }

    public Actor updateActor(UpsertActorRequest request, Integer id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy diễn viên với id = " + id));

        Actor existingActor = actorRepository.findByNameAndIdNot(request.getName(), id);
        if (existingActor != null) {
            // Nếu có Actor khác có cùng tên, ném một ngoại lệ hoặc trả về thông báo lỗi
            throw new IllegalArgumentException("Tên diễn viên đã tồn tại");
        }

        actor.setName(request.getName());
        actor.setAvatar(request.getAvatar());
        actor.setDescription(request.getDescription());

        actor.setUpdatedAt(new Date());
        return actorRepository.save(actor);
    }

    public void deleteActor(Integer id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy diễn viên với id = " + id));
        actorRepository.delete(actor);
    }
}
