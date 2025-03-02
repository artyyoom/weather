package ru.art.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.art.weather.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SignService {

    private final UserRepository userRepository;

    public void signIn() {
    }

    public void signUp() {}
}
