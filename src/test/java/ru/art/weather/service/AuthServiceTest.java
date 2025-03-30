package ru.art.weather.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import ru.art.weather.config.TestConfig;
import ru.art.weather.dto.RegistrationDto;
import ru.art.weather.exception.DataAlreadyExistsException;
import ru.art.weather.exception.DataNotFoundException;
import ru.art.weather.mapper.UserMapper;
import ru.art.weather.model.User;
import ru.art.weather.repository.SessionRepository;
import ru.art.weather.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private AuthService authService;

    @Test
    void registration() {
        RegistrationDto registrationDto = new RegistrationDto("test", "asdfasdf", "asdfasdf");
        User expectedUser = new User();
        expectedUser.setLogin("asdfasdf");
        expectedUser.setPassword("asdfasdf");

        when(userRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(userMapper.toEntity(any(RegistrationDto.class))).thenReturn(expectedUser);
        when(userRepository.create(any(User.class))).thenReturn(expectedUser);

        authService.registration(registrationDto);

        verify(userRepository).findByName(registrationDto.getLogin());
        verify(userMapper).toEntity(registrationDto);
        verify(userRepository).create(expectedUser);
        verifyNoMoreInteractions(userRepository, userMapper);

    }
}