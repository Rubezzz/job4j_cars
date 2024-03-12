package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.File;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class FileRepository {

    private final CrudRepository crudRepository;

    public Optional<File> get(int id) {
        return crudRepository.optional(
                "From File where id = :fId",
                File.class,
                Map.of("fId", id)
        );
    }

    public File save(File file) {
        crudRepository.run(session -> session.persist(file));
        return file;
    }

    public void delete(int id) {
        crudRepository.run(
                "delete from File where id = :fId",
                Map.of("fId", id)
        );
    }
}
