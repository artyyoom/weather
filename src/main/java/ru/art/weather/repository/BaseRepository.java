package ru.art.weather.repository;

import jakarta.persistence.OptimisticLockException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

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
        }
    }

//    public E create(E entity) {
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            try {
//                session.merge(entity);
//                session.getTransaction().commit();
//                return entity;
//            } catch (OptimisticLockException e) {
//                session.getTransaction().rollback();
//                throw new RuntimeException("Error in create: OptimisticLockException", e);
//            }
//        } catch (HibernateException e) {
//            throw new RuntimeException("Error in create", e);
//        }
//    }


    public E create(E entity) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            E mergedEntity = session.merge(entity);
            session.getTransaction().commit();

            return mergedEntity;
        }
    }

    public void delete(E entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        }
    }
}
