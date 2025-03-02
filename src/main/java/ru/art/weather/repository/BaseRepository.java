package ru.art.weather.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRepository<ID, E> {

    protected SessionFactory sessionFactory;
    protected Class<E> clazz;

    public E findById(ID id) {
        try(Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            E entity = session.find(clazz, id);
            session.getTransaction().commit();

            return entity;
        } catch (HibernateException e) {
            throw new RuntimeException("Error in findById");
        }
    }

    public E create(E entity) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();

            return entity;
        } catch (HibernateException e) {
            throw new RuntimeException("Error in create");
        }
    }
}
