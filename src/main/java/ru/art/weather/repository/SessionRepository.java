package ru.art.weather.repository;

import jakarta.persistence.NoResultException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.art.weather.model.Session;

import java.util.Optional;

@Repository
public class SessionRepository extends BaseRepository<Integer, Session> {

    public SessionRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Session> findByUserName(String name) {
        try(org.hibernate.Session session = sessionFactory.openSession()) {
            Session sessionRequest = session
                    .createQuery("FROM Session WHERE userId = :userId", Session.class)
                    .setParameter("userId", name)
                    .getSingleResult();
            return Optional.of(sessionRequest);
        } catch (NoResultException e) {
            return Optional.empty();
        } // TODO добавить database exception
    }
}
