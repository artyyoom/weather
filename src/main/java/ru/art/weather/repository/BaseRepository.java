package ru.art.weather.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRepository<ID, E> {

    protected final SessionFactory sessionFactory;
    protected Class<E> clazz;

    public BaseRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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
