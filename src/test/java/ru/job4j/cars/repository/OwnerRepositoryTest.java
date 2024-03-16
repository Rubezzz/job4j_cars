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
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class OwnerRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final OwnerRepository repository = new OwnerRepository(crudRepository);

    @AfterEach
    public void wipeTable() {
        crudRepository.run("DELETE FROM Owner", Map.of());
        crudRepository.run("DELETE FROM User", Map.of());
    }

    private Owner newOwner() {
        Owner owner = new Owner();
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("testPass");
        owner.setName("testOwner");
        owner.setUser(user);
        owner.setHistoryOwners(Set.of());
        return owner;
    }

    @Test
    public void whenAddNewOwnerThenRepositoryHasSameOwner() throws Exception {
        Owner owner = newOwner();
        repository.save(owner);
        Optional<Owner> result = repository.get(owner.getId());
        assertThat(owner).isEqualTo(result.get());
    }

    @Test
    public void whenDeleteOwnerThenRepositoryDoesntHaveOwner() throws Exception {
        Owner owner = newOwner();
        repository.save(owner);
        repository.delete(owner.getId());
        Optional<Owner> result = repository.get(owner.getId());
        assertThat(result).isEqualTo(Optional.empty());
    }
}