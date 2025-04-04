package ru.art.weather.repository;

import jakarta.persistence.OptimisticLockException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.art.weather.exception.DataBaseException;

import java.util.Optional;

@Repository
public abstract class BaseRepository<ID, E> {

    protected final SessionFactory sessionFactory;
    protected final Class<E> clazz;

    public BaseRepository(SessionFactory sessionFactory, Class<E> clazz) {
        this.sessionFactory = sessionFactory;
        this.clazz = clazz;
    }

    public Optional<E> findById(ID id) {
        try(Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            E entity = session.get(clazz, id);
            session.getTransaction().commit();

            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public E create(E entity) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            E mergedEntity = session.merge(entity);
            session.getTransaction().commit();

            return mergedEntity;
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public void delete(E entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
