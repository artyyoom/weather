package ru.art.weather.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.art.weather.dto.RegistrationDto;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.service.AuthService;
import ru.art.weather.util.DataValidator;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final DataValidator dataValidator;

    @GetMapping("/login")
    public String login(@RequestBody UserLoginDto userLoginDto, @CookieValue(value = "userId", required = false) String userId, HttpServletResponse response, Model model) {
        authService.login(userLoginDto, userId, response);

        model.addAttribute("userId", userId);
        return "html/main-page";
    }

    @GetMapping("/registration")
    public String registration(@RequestBody RegistrationDto registrationDto, Model model) {
        dataValidator.validateName(registrationDto.getLogin());
        dataValidator.validatePasswords(registrationDto.getPassword(), registrationDto.getRePassword());

        authService.registration(registrationDto);

        return "html/login";
    }
}
