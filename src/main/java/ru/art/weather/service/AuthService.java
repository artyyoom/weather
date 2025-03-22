package ru.art.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.art.weather.dto.RegistrationDto;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.exception.DataAlreadyExistsException;
import ru.art.weather.exception.DataNotFoundException;
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
    private final UserService userService;
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;

    public Optional<UUID> login(UserLoginDto userLoginDto, String sessionId) {

        User user = userRepository.findByName(userLoginDto.getLogin())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if (sessionId != null) {
            return checkSession(sessionId, user).map(Session::getId)
                    .or(() -> Optional.ofNullable(createSession(user).getId()));
        } else {
            Session session = getSessionByUser(user).orElseGet(() -> createSession(user));
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
                    return Optional.ofNullable(createSession(user));
                }

                return Optional.of(session);

            }).orElse(Optional.empty());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Optional<Session> getSessionByUser(User user) {
        return sessionRepository.findByUser(user);
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
                    throw new DataAlreadyExistsException("User already exists");
                });
        userRepository.create(createUser(registrationDto));
    }

    private User createUser(RegistrationDto registrationDto) {
        return userMapper.toEntity(registrationDto);
    }

    public boolean isUserMatchCookie(UserLoginDto userLoginDto, String sessionId) {
        Optional<User> user = userService.getUserBySessionId(sessionId);

        return user.filter(value -> userLoginDto.getLogin().equals(value.getLogin())).isPresent();
    }
}
