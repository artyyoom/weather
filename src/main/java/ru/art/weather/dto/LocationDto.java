package ru.art.weather.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {

    private String name;
    private double longitude;
    private double latitude;
}
