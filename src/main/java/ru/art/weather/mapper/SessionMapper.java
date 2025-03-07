package ru.art.weather.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.art.weather.dto.SessionResponseDto;
import ru.art.weather.model.Session;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionResponseDto toDto(Session session);
}
