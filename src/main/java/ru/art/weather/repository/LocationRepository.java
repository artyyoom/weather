package ru.art.weather.repository;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.art.weather.model.Location;
import ru.art.weather.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository extends BaseRepository<Integer, Location> {

    public LocationRepository(SessionFactory sessionFactory) { super(sessionFactory, Location.class); }

    public Optional<List<Location>> getLocationsByUser (User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Location> locationList = session.createQuery("FROM Location WHERE userId = :userId", Location.class)
                    .setParameter("userId", user)
                    .list();
            session.getTransaction().commit();
            return Optional.ofNullable(locationList);
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
