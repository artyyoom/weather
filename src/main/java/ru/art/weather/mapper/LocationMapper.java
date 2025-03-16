package ru.art.weather.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.art.weather.dto.WeatherDto;
import ru.art.weather.model.Location;
import ru.art.weather.model.User;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "weatherDto.name", target = "name")
    @Mapping(source = "weatherDto.coord.latitude", target = "latitude")
    @Mapping(source = "weatherDto.coord.longitude", target = "longitude")
    @Mapping(source = "user", target = "userId")
    Location toLocation(WeatherDto weatherDto, User user);
}
