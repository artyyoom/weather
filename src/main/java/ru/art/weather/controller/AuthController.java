package ru.art.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.art.weather.dto.RegistrationDto;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.service.AuthService;
import ru.art.weather.util.DataValidator;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final DataValidator dataValidator;

    @GetMapping("/")
    public String index(Model model) {
        UserLoginDto userLoginDto = new UserLoginDto();
        model.addAttribute("userLoginDto", userLoginDto);
        return "login";
    }

    @GetMapping("/login")
    public String redirectLogin() {
        //TODO сделать переброс на main-page если есть cookie и отправить список городов
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String redirectRegistration(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "registration";
    }

    @PostMapping("/login")
    //TODO возможно убрать model
    public String login(@ModelAttribute UserLoginDto userLoginDto, @CookieValue(value = "sessionId", required = false) String sessionId, HttpServletResponse response, Model model) {
        Optional<UUID> sessionUuid = authService.login(userLoginDto, sessionId);
        if (sessionUuid.isPresent()) {
            Cookie cookie = new Cookie("sessionId", sessionUuid.get().toString());
            cookie.setMaxAge(60 * 60 * 7);
            response.addCookie(cookie);
            model.addAttribute("sessionId", sessionUuid.get());
        } else {
            model.addAttribute("sessionId", sessionId);
        }

        return "main-page";
    }

    //TODO возможно переделать registration чтобы сам создавал session и cookie
    @PostMapping("/registration")
    public String registration(@ModelAttribute RegistrationDto registrationDto) {
        dataValidator.validateName(registrationDto.getLogin());
        dataValidator.validatePasswords(registrationDto.getPassword(), registrationDto.getRePassword());

        authService.registration(registrationDto);

        return "redirect:/";
    }
}
