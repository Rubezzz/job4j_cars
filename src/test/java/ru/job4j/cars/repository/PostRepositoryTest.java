package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final PostRepository repository = new PostRepository(crudRepository);

    @AfterEach
    public void wipeTable() {
        crudRepository.run("DELETE FROM File", Map.of());
        crudRepository.run("DELETE FROM Post", Map.of());
    }

    private Post newPost() {
        Post post = new Post();
        Car car = new Car();
        Make make = new Make();
        make.setName("testMake");
        Engine engine = new Engine();
        engine.setName("engineTest");
        car.setName("test");
        car.setMake(make);
        car.setEngine(engine);
        car.setHistoryOwners(Set.of());
        post.setCar(car);
        post.setDescription("TestPost");
        post.setPriceHistories(List.of());
        post.setFiles(List.of());
        return post;
    }

    @Test
    public void whenAddNewPostThenRepositoryHasSamePost() throws Exception {
        Post post = newPost();
        repository.save(post);
        Optional<Post> result = repository.get(post.getId());
        assertThat(post).isEqualTo(result.get());
    }

    @Test
    public void whenDeletePostThenRepositoryDoesntHavePost() throws Exception {
        Post post = newPost();
        repository.save(post);
        repository.delete(post.getId());
        Optional<Post> result = repository.get(post.getId());
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void whenFindNewPostThenRepositoryReturnNewPost() throws Exception {
        Post post1 = newPost();
        post1.setCreation(LocalDateTime.now());
        Post post2 = newPost();
        post2.setCreation(LocalDateTime.now().minusDays(2));
        repository.save(post1);
        repository.save(post2);
        Collection<Post> result = repository.findNew();
        Collection<Post> expected = List.of(post1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenFindWidthPhotoPostThenRepositoryReturnPostWidthPhoto() throws Exception {
        Post post1 = newPost();
        File file = new File();
        file.setName("testFile");
        file.setPath("testFilePath");
        post1.setFiles(List.of(file));
        Post post2 = newPost();
        repository.save(post1);
        repository.save(post2);
        Collection<Post> result = repository.findWidthPhoto();
        Collection<Post> expected = List.of(post1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenFindByMakePostThenRepositoryReturnPostByMake() throws Exception {
        Post post1 = newPost();
        Make make = new Make();
        make.setName("testFile");
        post1.getCar().setMake(make);
        Post post2 = newPost();
        repository.save(post1);
        repository.save(post2);
        Collection<Post> result = repository.findByMake(make);
        Collection<Post> expected = List.of(post1);
        assertThat(result).isEqualTo(expected);
    }
}