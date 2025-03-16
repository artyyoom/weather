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
    private String name;

    private Sys sys;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private Weather[] weather;

    @JsonProperty("coord")
    private Coord coord;

    public String getCountry() {
        return sys != null ? sys.getCountry() : null;
    }

    public double getTemperature() {
        return main != null ? Math.round((main.getTemp() - 273.15) * 100.0) / 100.0 : 0.0;
    }

    public double getApparentTemperature() {
        return main != null ? Math.round((main.getFeelsLike() - 273.15) * 100.0) / 100.0 : 0.0;
    }

    public String getDescription() {
        if (weather != null && weather.length > 0) {
            return weather[0].getDescription();
        }
        return null;
    }

    public String getIcon() {
        if (weather != null && weather.length > 0) {
            return weather[0].getIcon();
        }
        return null;
    }

    public int getHumidity() {
        return main != null ? main.getHumidity() : 0;
    }

    public double getLongitude() {
        return coord != null ? coord.getLongitude() : 0.0;
    }

    public double getLatitude() {
        return coord != null ? coord.getLatitude() : 0.0;
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
        private String description;
        private String icon;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coord {
        @JsonProperty("lon")
        private double longitude;
        @JsonProperty("lat")
        private double latitude;
    }
}
