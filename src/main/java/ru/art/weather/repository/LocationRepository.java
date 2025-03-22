package ru.art.weather.repository;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.art.weather.exception.DataBaseException;
import ru.art.weather.model.Location;
import ru.art.weather.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository extends BaseRepository<Integer, Location> {

    public LocationRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Location.class);
    }

    public Optional<List<Location>> getLocationsByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Location> locationList = session.createQuery("FROM Location WHERE userId = :userId", Location.class)
                    .setParameter("userId", user)
                    .list();
            session.getTransaction().commit();
            return Optional.ofNullable(locationList);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    //TODO
    public void deleteByUserAndCity(User user, String paramCity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Location WHERE userId = :userId AND name = :city")
                    .setParameter("userId", user)
                    .setParameter("city", paramCity)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (NoResultException ignored) {
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public Optional<Location> getByUserAndCity(User user, String city) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Location location = session.createQuery("FROM Location WHERE userId = :userId AND name = :name", Location.class)
                    .setParameter("userId", user)
                    .setParameter("name", city)
                    .getSingleResult();
            session.getTransaction().commit();
            return Optional.of(location);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
