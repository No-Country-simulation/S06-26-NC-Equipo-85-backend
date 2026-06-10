package com.appbit.orientation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrientationRequest(
        @NotBlank(message = "query is required")
        @Size(max = 1000, message = "query must not exceed 1000 characters")
        String query
) {
}
