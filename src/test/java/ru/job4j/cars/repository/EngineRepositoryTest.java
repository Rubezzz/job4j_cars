package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EngineRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    @AfterEach
    public void wipeTable() {
        crudRepository.run("DELETE FROM Engine", Map.of());
    }

    @Test
    public void whenAddNewEngineThenRepositoryHasSameEngine() throws Exception {
        Engine engine = new Engine();
        engine.setName("testName");
        engineRepository.save(engine);
        Optional<Engine> result = engineRepository.get(engine.getId());
        assertThat(engine).isEqualTo(result.get());
    }

    @Test
    public void whenDeleteEngineThenRepositoryDoesntHaveEngine() throws Exception {
        Engine engine = new Engine();
        engine.setName("testName");
        engineRepository.save(engine);
        engineRepository.delete(engine.getId());
        Optional<Engine> result = engineRepository.get(engine.getId());
        assertThat(result).isEqualTo(Optional.empty());
    }
}