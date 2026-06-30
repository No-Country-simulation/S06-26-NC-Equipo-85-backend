package com.appbit.health.dto;

import com.appbit.common.enums.MoodEmoji;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckinRequest {

    @NotNull(message = "Emoji is required")
    private MoodEmoji emoji;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;

    private String context;
}
