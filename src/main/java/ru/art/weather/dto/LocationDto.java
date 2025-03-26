package ru.art.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {

    private String name;

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lon")
    private double longitude;

    private String country;
}
