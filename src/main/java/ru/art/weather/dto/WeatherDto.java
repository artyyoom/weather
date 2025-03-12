package ru.art.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    @JsonProperty("name")
    private String cityName;

    private Sys sys;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private Weather[] weather;

    public String getCountry() {
        return sys != null ? sys.getCountry() : null;
    }

    public double getTemperature() {
        return main != null ? main.getTemp() : 0.0;
    }

    public double getApparentTemperature() {
        return main != null ? main.getFeelsLike() : 0.0;
    }

    public String getDescription() {
        if (weather != null && weather.length > 0) {
            return weather[0].getDescription();
        }
        return null;
    }

    public int getHumidity() {
        return main != null ? main.getHumidity() : 0;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        @JsonProperty("country")
        private String country;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        @JsonProperty("temp")
        private double temp;

        @JsonProperty("feels_like")
        private double feelsLike;

        private int humidity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        @JsonProperty("description")
        private String description;
    }
}
