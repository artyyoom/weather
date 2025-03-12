package ru.art.weather.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.art.weather.model.Location;

@Repository
public class LocationRepository extends BaseRepository<Integer, Location> {

    public LocationRepository(SessionFactory sessionFactory) { super(sessionFactory, Location.class); }

}
