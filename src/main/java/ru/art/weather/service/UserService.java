package ru.art.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.art.weather.exception.DataNotFoundException;
import ru.art.weather.model.User;
import ru.art.weather.repository.SessionRepository;
import ru.art.weather.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public Optional<User> getUserBySessionId(String sessionId) {
        return sessionRepository.findById(UUID.fromString(sessionId))
                .flatMap(session -> (userRepository.findById(session.getUserId().getId())));
    }

    public User getUserByName(String userName) {
        return userRepository.findByName(userName).orElseThrow(() -> new DataNotFoundException("User not found"));
    }
}
