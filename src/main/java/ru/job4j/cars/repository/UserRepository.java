package ru.job4j.cars.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepository {

    @NonNull
    private final SessionFactory sf;
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

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
            logger.error(e.getMessage());
        } finally {
            session.close();
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
            logger.error(e.getMessage());
        } finally {
            session.close();
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
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        List<User> rsl = new ArrayList<>();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "FROM User u ORDER BY u.id", User.class);
            rsl = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.getTransaction().rollback();
            session.close();
        }
        return rsl;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "FROM User u WHERE u.id = :fId", User.class)
                    .setParameter("fId", userId);
            rsl = query.uniqueResultOptional();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.getTransaction().rollback();
            session.close();
        }
        return rsl;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        List<User> rsl = new ArrayList<>();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "FROM User u WHERE u.login LIKE :fKey", User.class)
                    .setParameter("fKey", "%" + key + "%");
            rsl = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.getTransaction().rollback();
            session.close();
        }
        return rsl;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                            "FROM User u WHERE u.login = :fLogin", User.class)
                    .setParameter("fLogin", login);
            rsl = query.uniqueResultOptional();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.getTransaction().rollback();
            session.close();
        }
        return rsl;
    }
}