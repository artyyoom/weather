package ru.art.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.art.weather.dto.LocationDto;
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
        User user = userService.getUserBySessionId(sessionId).orElseThrow(RuntimeException::new);

        List<WeatherDto> locations = weatherService.getLocations(user);
        model.addAttribute("locations", locations);
        model.addAttribute("userName", user.getLogin());

        return "main-page";
    }

    @GetMapping("search-results")
    public String searchResults(@CookieValue(value = "sessionId", required = false) String sessionId, @RequestParam(name = "city") String city, Model model) {
        if (sessionId == null) {
            return "redirect:/login";
        }

        List<WeatherDto> locations = weatherService.getWeatherByCity(city);
        model.addAttribute("locations", locations);
        return "search-results";
    }

    @PostMapping("add-weather")
    public String addWeather(@CookieValue(value = "sessionId", required = false) String sessionId,@ModelAttribute LocationDto locationDto) {
        User user = userService.getUserBySessionId(sessionId).orElseThrow(RuntimeException::new);

        weatherService.addWeather(locationDto, user);

        return "redirect:/main-page";
    }

    @GetMapping("delete-weather")
    public String deleteWeather(@CookieValue(value = "sessionId", required = false) String sessionId, @RequestParam(name = "city") String city) {
        weatherService.deleteWeather(userService.getUserBySessionId(sessionId).orElseThrow(RuntimeException::new), city);
        return "redirect:/main-page";
    }
}
