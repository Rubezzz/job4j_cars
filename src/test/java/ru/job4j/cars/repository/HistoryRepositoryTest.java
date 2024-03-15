package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HistoryRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HistoryRepository repository = new HistoryRepository(crudRepository);

    @AfterEach
    public void wipeTable() {
        crudRepository.run("DELETE FROM HistoryOwner", Map.of());
        crudRepository.run("DELETE FROM Car", Map.of());
    }

    private HistoryOwner newHistory() {
        Car car = new Car();
        car.setName("testCar");
        Make make = new Make();
        make.setName("testMake");
        Engine engine = new Engine();
        engine.setName("engineTest");
        car.setMake(make);
        car.setEngine(engine);
        Owner owner = new Owner();
        owner.setName("testOwner");
        HistoryOwner history = new HistoryOwner();
        history.setCar(car);
        history.setOwner(owner);
        return history;
    }

    @Test
    public void whenAddNewHistoryThenRepositoryHasSameHistory() throws Exception {
        HistoryOwner history = newHistory();
        repository.save(history);
        Optional<HistoryOwner> result = repository.get(history.getId());
        assertThat(history).isEqualTo(result.get());
    }

    @Test
    public void whenDeleteHistoryThenRepositoryDoesntHaveHistory() throws Exception {
        HistoryOwner history = newHistory();
        repository.save(history);
        repository.delete(history.getId());
        Optional<HistoryOwner> result = repository.get(history.getId());
        assertThat(result).isEqualTo(Optional.empty());
    }
}