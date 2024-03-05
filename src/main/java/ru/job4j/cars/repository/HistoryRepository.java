package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.History;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HistoryRepository {

    private final CrudRepository crudRepository;

    private Optional<History> get(int id) {
        return crudRepository.optional(
                "From History where id = :fId",
                History.class,
                Map.of("fId", id)
        );
    }

    private History save(History history) {
        crudRepository.run(session -> session.persist(history));
        return history;
    }

    public void delete(int id) {
        crudRepository.run(
                "delete from History where id = :fId",
                Map.of("fId", id)
        );
    }
}
