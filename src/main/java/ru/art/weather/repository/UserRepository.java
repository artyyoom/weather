package ru.art.weather.repository;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.art.weather.model.User;

import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<Integer, User> {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    public Optional<User> findByName(String name) {
        try(Session session = sessionFactory.openSession()) {
            User user = session
                    .createQuery("FROM User WHERE Login = :login", User.class)
                    .setParameter("login", name)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } // TODO добавить database exception
    }
}
