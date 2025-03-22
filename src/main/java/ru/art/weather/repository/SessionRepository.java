package ru.art.weather.repository;

import jakarta.persistence.NoResultException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.art.weather.exception.DataBaseException;
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
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
        // TODO добавить database exception
    }

    public Optional<Session> findByIdAndUser(UUID sessionId, User user) {
        try(org.hibernate.Session session = sessionFactory.openSession()) {
            Session sessionRequest = session
                    .createQuery("FROM Session WHERE id = :sessionId AND userId = :userId", Session.class)
                    .setParameter("sessionId", sessionId)
                    .setParameter("userId", user)
                    .getSingleResult();
            return Optional.of(sessionRequest);
        } catch (NoResultException e) {
            return Optional.empty();
        } // TODO добавить database exception
    }
}
