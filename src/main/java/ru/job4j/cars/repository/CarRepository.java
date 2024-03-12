package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Car;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class CarRepository {

    private final CrudRepository crudRepository;

    public Optional<Car> get(int id) {
        return crudRepository.optional(
                "From Car where id = :fId",
                Car.class,
                Map.of("fId", id)
        );
    }

    public Car save(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    public void delete(int id) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", id)
        );
    }

    public void update(Car car) {
        crudRepository.run(
                "UPDATE Car SET name = :fName, engine_id = :fEngine_id  WHERE id = :fId",
                Map.of("fName", car.getName(), "fEngine_id", car.getEngine().getId(), "fId", car.getId())
        );
    }
}
