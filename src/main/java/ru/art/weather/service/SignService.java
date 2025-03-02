package ru.art.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.art.weather.repository.SignRepository;

@Service
@RequiredArgsConstructor
public class SignService {

    private final SignRepository signRepository;

    public void signIn() {
    }

    public void signUp() {}
}
