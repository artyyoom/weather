package ru.art.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.art.weather.service.SignService;
import ru.art.weather.util.DataValidator;

@Controller
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;
    private final DataValidator dataValidator;

    @GetMapping("/sign-in")
    public String signIn() {

        signService.signIn();

        return "sign/sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp() {

        signService.signUp();

        return "sign/sign-up";
    }
}
