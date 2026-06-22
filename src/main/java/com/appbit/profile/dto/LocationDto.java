package com.appbit.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private String continent;
    private String country;
    private String state;
    private String city;
    private Double latitude;
    private Double longitude;
}
