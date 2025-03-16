package ru.art.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.art.weather.dto.RegistrationDto;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.mapper.UserMapper;
import ru.art.weather.model.Session;
import ru.art.weather.model.User;
import ru.art.weather.repository.SessionRepository;
import ru.art.weather.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;

    public Optional<UUID> login(UserLoginDto userLoginDto, String sessionId) {

        User user = userRepository.findByName(userLoginDto.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (sessionId != null) {
            return checkSession(sessionId, user).map(Session::getId)
                    .or(() -> Optional.ofNullable(createSession(user).getId()));
        }
        else {
            Session session = getSessionByUser(user);
            return Optional.ofNullable(session.getId());
        }
    }

    public Optional<Session> checkSession(String sessionId, User user) {
        try {
            UUID sessionUuid = UUID.fromString(sessionId);
            Optional<Session> sessionOptional = sessionRepository.findById(sessionUuid);

            return sessionOptional.map(session -> {
                LocalDateTime now = LocalDateTime.now();

                if (now.isBefore(session.getExpiresAt())) {
                    sessionRepository.delete(session);
                    return Optional.of(createSession(user));
                }

                return Optional.of(session);

            }).orElse(Optional.empty());
        }
        catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    //TODO сделать проверку на время сессии (checkSession разделить)
    private Session getSessionByUser(User user) {
        return sessionRepository.findByUser(user)
                .orElseGet(() -> createSession(user));
    }

    private Session createSession(User user) {
        LocalDateTime date = LocalDateTime.now().minusDays(7);

        Session session = Session.builder()
                .userId(user)
                .expiresAt(date)
                .build();
        return sessionRepository.create(session);
    }

    public void registration(RegistrationDto registrationDto) {
        userRepository.findByName(registrationDto.getLogin())
                .ifPresent(existingUser -> {
                    throw new RuntimeException("User already exists");
                });
        userRepository.create(createUser(registrationDto));
    }

    private User createUser(RegistrationDto registrationDto) {
        return userMapper.toEntity(registrationDto);
    }
}
