package ru.art.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.art.weather.dto.WeatherDto;
import ru.art.weather.model.Location;
import ru.art.weather.repository.LocationRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final LocationRepository locationRepository;
    private final ObjectMapper objectMapper;

    //TODO проверить на правильность города

    public void getWeather(String city) throws URISyntaxException, IOException, InterruptedException {

        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=6ca196c0d8b8abaee4e00a273b662353", city);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Location location = objectMapper.readValue(response.body(), Location.class);
        locationRepository.create(location);

        WeatherDto weatherDto = objectMapper.readValue(response.body(), WeatherDto.class);

        //TODO сделать dto
    }
}
