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
public class LocationDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("coord")
    private Coord coord;


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
    public static class Coord {
        @JsonProperty("lon")
        private double longitude;
        @JsonProperty("lat")
        private double latitude;
    }
}
