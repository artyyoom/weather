package ru.art.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;
import ru.art.weather.dto.WeatherDto;
import ru.art.weather.mapper.LocationMapper;
import ru.art.weather.model.Location;
import ru.art.weather.model.User;
import ru.art.weather.repository.LocationRepository;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final LocationRepository locationRepository;
    private final ObjectMapper objectMapper;
    private final LocationMapper locationMapper;
    private final HttpClient httpClient;


    public List<WeatherDto> getLocations(User user) {
        return locationRepository.getLocationsByUser(user)
                .stream()
                .flatMap(Collection::stream)
                .map(this::getWeatherByLocation)
                .collect(Collectors.toList());
    }

    private WeatherDto getWeatherByLocation(Location location) {
        try {
            String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=6ca196c0d8b8abaee4e00a273b662353",
                    location.getLatitude(), location.getLongitude());

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), WeatherDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //TODO проверить на правильность города
    public void addWeather(User user, String paramCity) {
        try {
            String decodedCity = StringEscapeUtils.unescapeHtml4(paramCity);
            String city = URLEncoder.encode(decodedCity, StandardCharsets.UTF_8);

            String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=6ca196c0d8b8abaee4e00a273b662353", city);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            WeatherDto weatherDto = objectMapper.readValue(response.body(), WeatherDto.class);
            createLocation(weatherDto, user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //TODO
    public void deleteWeather(User user, String paramCity) {}

    private void createLocation(WeatherDto weatherDto, User user) {
        locationRepository.create(locationMapper.toLocation(weatherDto, user));
    }
}
