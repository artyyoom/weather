package ru.art.weather.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.art.weather.dto.LoginResponseDto;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.mapper.SessionMapper;
import ru.art.weather.model.Session;
import ru.art.weather.model.User;
import ru.art.weather.repository.SessionRepository;
import ru.art.weather.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public LoginResponseDto login(UserLoginDto userLoginDto, HttpServletRequest request) {
        Optional<User> user = userRepository.findByName(userLoginDto.getLogin());
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Session session;
        String cookie = extractCookie(request, "userID");
        if (cookie != null) {
            session = sessionRepository.findById(Integer.parseInt(cookie));
        }
        else {
            session = createSession(user);
        }

        return LoginResponseDto.builder()
                .session(session)
                .user(user.get())
                .build();
    }

    private Session createSession(Optional<User> user) {

        Session session = Session.builder()
                .userId(user.get())
                .expiresAt(new Date())
                .build();

        return sessionRepository.create(session);
    }

    private String extractCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void signUp() {}
}
