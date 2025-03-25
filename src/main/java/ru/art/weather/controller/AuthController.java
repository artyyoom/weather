package ru.art.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.art.weather.dto.RegistrationDto;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.model.Session;
import ru.art.weather.model.User;
import ru.art.weather.repository.SessionRepository;
import ru.art.weather.service.AuthService;
import ru.art.weather.service.UserService;
import ru.art.weather.util.DataValidator;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final SessionRepository sessionRepository;
    private final DataValidator dataValidator;

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String redirectLogin(@CookieValue(value = "sessionId", required = false) String sessionId, Model model) {

        model.addAttribute("userLoginDto", new UserLoginDto());
        return "login";
    }

    @GetMapping("/registration")
    public String redirectRegistration(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "registration";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto, @CookieValue(value = "sessionId", required = false) String sessionId, HttpServletResponse response, Model model) {

        User user = userService.getUserByName(userLoginDto.getLogin());
        authService.checkPassword(user, userLoginDto.getPassword());


        if (sessionId != null && !authService.isUserMatchCookie(userLoginDto, sessionId)) {
            Optional<Session> byUser = sessionRepository.findByUser(userService.getUserByName(userLoginDto.getLogin()));
            sessionId = byUser.map(session -> session.getId().toString()).orElse(null);
        }

        Optional<UUID> sessionUuid = authService.login(userLoginDto, sessionId);

        if (sessionUuid.isPresent()) {
            Cookie cookie = new Cookie("sessionId", sessionUuid.get().toString());
            response.addCookie(cookie);
            model.addAttribute("sessionId", sessionUuid.get());
        }

        return "redirect:/main-page";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute RegistrationDto registrationDto) {
        dataValidator.validateName(registrationDto.getLogin());
        dataValidator.validatePasswords(registrationDto.getPassword(), registrationDto.getRePassword());

        authService.registration(registrationDto);

        return "redirect:/";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("sessionId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
