package ru.art.weather.mapper;

import org.mapstruct.Mapper;
import ru.art.weather.dto.UserDto;
import ru.art.weather.model.User;

@Mapper
public interface ObjectMapper {

    User toEntity(UserDto userDto);
}
