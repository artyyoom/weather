package ru.art.weather.controller;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.art.weather.dto.UserDto;
import ru.art.weather.service.SignService;
import ru.art.weather.util.DataValidator;

@Controller
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;
    private final DataValidator dataValidator;

    @GetMapping("/sign-in")
    public String signIn(@RequestBody UserDto userDto) {

        signService.signIn( );
        System.out.println();

        return "html/sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp() {

        signService.signUp();

        return "html/sign-up";
    }
}
