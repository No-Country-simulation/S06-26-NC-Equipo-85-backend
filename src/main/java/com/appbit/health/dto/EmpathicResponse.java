package com.appbit.health.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpathicResponse {

    @JsonProperty("response")
    private String response;
}
