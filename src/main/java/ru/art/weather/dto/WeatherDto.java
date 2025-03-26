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

    @JsonProperty("location")
    private Location location;

    @JsonProperty("current")
    private Current current;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        @JsonProperty("name")
        private String name;

        @JsonProperty("region")
        private String region;

        @JsonProperty("country")
        private String country;

        @JsonProperty("lat")
        private double latitude;

        @JsonProperty("lon")
        private double longitude;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        @JsonProperty("temp_c")
        private double temperatureCelsius;

        @JsonProperty("condition")
        private Condition condition;

        @JsonProperty("wind_kph")
        private double windSpeedKph;

        @JsonProperty("humidity")
        private int humidity;

        @JsonProperty("cloud")
        private int cloudCover;

        @JsonProperty("feelslike_c")
        private double feelsLikeCelsius;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Condition {
        @JsonProperty("text")
        private String description;

        @JsonProperty("icon")
        private String iconUrl;
    }
}
