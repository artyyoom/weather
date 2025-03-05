package ru.art.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.art.weather.dto.LoginResponseDto;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.service.SignService;

@Controller
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request, HttpServletResponse response) {

        LoginResponseDto loginDto = signService.login(userLoginDto, request);

        Cookie cookie = new Cookie("userID", String.valueOf(loginDto.getSession().getId()));
        response.addCookie(cookie);

        return "html/sign-in";
    }

    @PostMapping("/sign-up")
    public String signUp() {

        signService.signUp();

        return "html/sign-up";
    }
}
