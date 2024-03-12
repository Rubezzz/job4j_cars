package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Make;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@AllArgsConstructor
class CarRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final CarRepository carRepository = new CarRepository(crudRepository);

    @AfterEach
    public void wipeTable() {
        crudRepository.run("DELETE FROM Car", Map.of());
    }

    private Car newCar() {
        Car car = new Car();
        Make make = new Make();
        make.setName("testMake");
        Engine engine = new Engine();
        engine.setName("engineTest");
        car.setName("test");
        car.setMake(make);
        car.setEngine(engine);
        car.setHistoryOwners(Set.of());
        return car;
    }

    @Test
    public void whenAddNewCarThenRepositoryHasSameCar() throws Exception {
        Car car = newCar();
        carRepository.save(car);
        Optional<Car> result = carRepository.get(car.getId());
        assertThat(car).isEqualTo(result.get());
    }

    @Test
    public void whenDeleteCarThenRepositoryDoesntHaveCar() throws Exception {
        Car car = newCar();
        carRepository.save(car);
        carRepository.delete(car.getId());
        Optional<Car> result = carRepository.get(car.getId());
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenUpdateCarThenRepositoryHasUpdatedCar() throws Exception {
        Car car = newCar();
        carRepository.save(car);
        car.setName("updatedName");
        carRepository.update(car);
        Optional<Car> result = carRepository.get(car.getId());
        assertThat(result.get().getName()).isEqualTo("updatedName");
    }
}