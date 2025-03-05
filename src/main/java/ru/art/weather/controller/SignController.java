package ru.art.weather.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.service.AuthService;

@Controller
@RequiredArgsConstructor
public class SignController {
    private final AuthService signService;

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto userLoginDto, @CookieValue("userId") String userId, HttpServletResponse response) {

        //TODO check userLoginDto in database

        signService.login(userLoginDto, userId, response);

        return "html/main-page";
    }

    @PostMapping("/sign-up")
    public String signUp() {

        signService.signUp();

        return "html/sign-up";
    }
}
