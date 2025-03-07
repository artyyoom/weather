package ru.art.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationDto {
    private String login;
    private String password;
    private String rePassword;
}
