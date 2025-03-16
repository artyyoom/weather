package ru.art.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.art.weather.dto.WeatherDto;
import ru.art.weather.model.User;
import ru.art.weather.service.UserService;
import ru.art.weather.service.WeatherService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final UserService userService;

    @GetMapping("main-page")
    public String mainPage(@CookieValue(value = "sessionId", required = false) String sessionId, Model model) {
        User user = userService.getUserBySessionId(sessionId);

        List<WeatherDto> locations = weatherService.getLocations(user);
        model.addAttribute("locations", locations);

        return "main-page";
    }

    @PostMapping("add-weather")
    public String addWeather(@CookieValue(value = "sessionId", required = false) String sessionId,@RequestParam(name = "city") String city) {
        User user = userService.getUserBySessionId(sessionId);

        weatherService.addWeather(user, city);

        return"redirect:/main-page";
    }
}
