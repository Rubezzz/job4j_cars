package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Engine;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class EngineRepository {

    private final CrudRepository crudRepository;

    private Optional<Engine> get(int id) {
        return crudRepository.optional(
                "From Engine where id = :fId",
                Engine.class,
                Map.of("fId", id)
        );
    }

    private Engine save(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
        return engine;
    }

    public void delete(int id) {
        crudRepository.run(
                "delete from Owner where id = :fId",
                Map.of("fId", id)
        );
    }
}
