package ru.art.weather.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.art.weather.dto.LocationDto;
import ru.art.weather.model.Location;
import ru.art.weather.model.User;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user", target = "userId")
    Location toLocation(LocationDto locationDto, User user);
}
