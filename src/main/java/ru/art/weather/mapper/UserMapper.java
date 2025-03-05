package ru.art.weather.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.model.User;

@Mapper
@Component
public interface UserMapper {

    User toEntity(UserLoginDto userDto);
}
