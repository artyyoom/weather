package ru.art.weather.mapper;

import org.mapstruct.Mapper;
import ru.art.weather.dto.RegistrationDto;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserLoginDto userDto);
    User toEntity(RegistrationDto user);
}