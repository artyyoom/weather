package ru.art.weather.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.art.weather.model.Session;
import ru.art.weather.model.User;

@Getter
@Setter
@Builder
public class LoginResponseDto {

    private Session session;
    private User user;

}
