package ru.art.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;
import ru.art.weather.dto.LocationDto;
import ru.art.weather.dto.WeatherDto;
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
            String url = String.format("https://api.weatherapi.com/v1/current.json?key=da96ad96c28748c2aaf140646252603&q=%s,%s&aqi=no",
                    location.getLatitude(), location.getLongitude());

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), WeatherDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<LocationDto> getWeatherByCity(String city) {
        try {
//            String decodedCity = StringEscapeUtils.unescapeHtml4(city);

            String url = String.format("https://api.weatherapi.com/v1/search.json?key=da96ad96c28748c2aaf140646252603&q=%s&aqi=no", city);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            List<LocationDto> locations = objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, LocationDto.class));

            if (locations.isEmpty()) {
                throw new Exception("Location not found");
            }

            return locations;

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
        locationRepository.deleteByUserAndLocation(user, locationDto);
    }

    private void createLocation(LocationDto locationDto, User user) {
        locationRepository.getByUserAndLocation(user, locationDto)
                .orElseGet(() -> locationRepository.create(locationMapper.toLocation(locationDto, user)));
    }
}
