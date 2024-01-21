package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            int userId = (Integer) session.save(user);
            user.setId(userId);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE User SET login = :fLogin, password = :fPassword  WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(
                "FROM User u ORDER BY u.id", User.class);
        return query.getResultList();
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(
                "FROM User u WHERE u.id = :fId", User.class)
                .setParameter("fId", userId);
        return Optional.ofNullable(query.getSingleResult());
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(
                "FROM User u WHERE u.login LIKE :fKey", User.class)
                .setParameter("fKey", "%" + key + "%");
        return query.getResultList();
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(
                        "FROM User u WHERE u.login = :fLogin", User.class)
                .setParameter("fLogin", login);
        return Optional.ofNullable(query.getSingleResult());
    }
}