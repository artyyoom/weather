package ru.art.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;
import ru.art.weather.dto.LocationDto;
import ru.art.weather.dto.WeatherDto;
import ru.art.weather.dto.WeatherResponseDto;
import ru.art.weather.exception.DataNotFoundException;
import ru.art.weather.mapper.LocationMapper;
import ru.art.weather.model.Location;
import ru.art.weather.model.User;
import ru.art.weather.repository.LocationRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    public List<WeatherDto> getWeatherByCity(String city) {
        try {
            String decodedCity = StringEscapeUtils.unescapeHtml4(city);

            String url = String.format("https://api.openweathermap.org/data/2.5/find?q=%s&appid=6ca196c0d8b8abaee4e00a273b662353", decodedCity);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            WeatherResponseDto weatherDto = objectMapper.readValue(response.body(), WeatherResponseDto.class);
            System.out.println(weatherDto.getWeather().size());
            return weatherDto.getWeather();

        } catch (Exception e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }

    public void addWeather(LocationDto location, User user) {
        try {
            createLocation(location, user);
        } catch (Exception e) {
            throw new DataNotFoundException("Weather not found");
        }
    }

    //TODO
    public void deleteWeather(User user, LocationDto locationDto) {
        locationRepository.deleteByUserAndCity(user, locationDto);
    }

    private void createLocation(LocationDto locationDto, User user) {
        locationRepository.getByUserAndLocation(user, locationDto)
                .orElseGet(() -> locationRepository.create(locationMapper.toLocation(locationDto, user)));
    }
}
