package ru.art.weather.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public void login(UserLoginDto userLoginDto, String userId, HttpServletResponse response) {
        Optional<User> userOptional = userRepository.findByName(userLoginDto.getLogin());
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));

        if (userId == null) {
            Session session = createSession(user);
            Cookie cookie = new Cookie("userID", session.getId().toString());
            response.addCookie(cookie);
        }
    }

    private Session createSession(User user) {

        Session session = Session.builder()
                .userId(user)
                .expiresAt(new Date())
                .build();

        return sessionRepository.create(session);
    }



//    private String extractCookie(HttpServletRequest request, String name) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(name)) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }

    public void signUp() {}
}
