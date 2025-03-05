package ru.art.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.art.weather.model.User;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SessionResponseDto {
    private UUID id;
    private User userId;
    private Date expiresAt;
}
