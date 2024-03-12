package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.HistoryOwner;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HistoryRepository {

    private final CrudRepository crudRepository;

    public Optional<HistoryOwner> get(int id) {
        return crudRepository.optional(
                "From HistoryOwner where id = :fId",
                HistoryOwner.class,
                Map.of("fId", id)
        );
    }

    public HistoryOwner save(HistoryOwner history) {
        crudRepository.run(session -> session.persist(history));
        return history;
    }

    public void delete(int id) {
        crudRepository.run(
                "delete from HistoryOwner where id = :fId",
                Map.of("fId", id)
        );
    }
}
