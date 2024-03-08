package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Make;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class PostRepository {

    private final CrudRepository crudRepository;

    private Post save(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    private Optional<Post> get(int id) {
        return crudRepository.optional(
                "From Post where id = :fId",
                Post.class,
                Map.of("fId", id)
        );
    }

    public Collection<Post> findNew() {
        return crudRepository.query(
                "FROM Post WHERE creation > :fDate",
                Post.class,
                Map.of("fDate", LocalDateTime.now().minusDays(1))
        );
    }

    public Collection<Post> findWidthPhoto() {
        return crudRepository.query(
                "FROM Post WHERE file_id :fFileId",
                Post.class,
                Map.of("fFileId", "IS NULL")
        );
    }

    public Collection<Post> findByMake(Make make) {
        return crudRepository.query(
                "FROM Post p WHERE p.make_id = (FROM Make m WHERE m.id = :fMakeId)",
                Post.class,
                Map.of("fMakeId", make.getId())
        );
    }
}
