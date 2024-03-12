package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import ru.job4j.cars.model.File;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class FileRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final FileRepository fileRepository = new FileRepository(crudRepository);

    @AfterEach
    public void wipeTable() {
        crudRepository.run("DELETE FROM File", Map.of());
    }

    @Test
    public void whenAddNewFileThenRepositoryHasSameFile() throws Exception {
        File file = new File();
        file.setName("testName");
        file.setPath("testPath");
        fileRepository.save(file);
        Optional<File> result = fileRepository.get(file.getId());
        assertThat(file).isEqualTo(result.get());
    }

    @Test
    public void whenDeleteFileThenRepositoryDoesntHaveFile() throws Exception {
        File file = new File();
        file.setName("testName");
        file.setPath("testPath");
        fileRepository.save(file);
        fileRepository.delete(file.getId());
        Optional<File> result = fileRepository.get(file.getId());
        assertThat(result).isEqualTo(Optional.empty());
    }
}