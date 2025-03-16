package ru.art.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.art.weather.model.Session;
import ru.art.weather.model.User;
import ru.art.weather.repository.SessionRepository;
import ru.art.weather.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public User getUserBySessionId(String sessionId) {
        Session session = sessionRepository.findById(UUID.fromString(sessionId)).orElseThrow(RuntimeException::new);
        return userRepository.findById(session.getUserId().getId()).orElseThrow(RuntimeException::new);
    }
}
