package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Owner;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class OwnerRepository {

    private final CrudRepository crudRepository;

    private Optional<Owner> get() {
        return crudRepository.optional("From Owner", Owner.class, Map.of());
    }

    private Owner save(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
        return owner;
    }

    public void delete(int userId) {
        crudRepository.run(
                "delete from Owner where id = :fId",
                Map.of("fId", userId)
        );
    }
}
