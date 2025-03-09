package ru.art.weather.repository;

import jakarta.persistence.NoResultException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.art.weather.model.Session;
import ru.art.weather.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepository extends BaseRepository<UUID, Session> {

    public SessionRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Session.class);
    }

    public Optional<Session> findByUser(User user) {
        try(org.hibernate.Session session = sessionFactory.openSession()) {
            Session sessionRequest = session
                    .createQuery("FROM Session WHERE userId = :userId", Session.class)
                    .setParameter("userId", user)
                    .getSingleResult();
            return Optional.of(sessionRequest);
        } catch (NoResultException e) {
            return Optional.empty();
        } // TODO добавить database exception
    }
}
