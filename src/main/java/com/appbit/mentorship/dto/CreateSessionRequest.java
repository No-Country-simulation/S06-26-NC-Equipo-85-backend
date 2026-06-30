package com.appbit.mentorship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSessionRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 150)
    private String title;

    @NotNull(message = "Schedule date is required")
    @Future(message = "Schedule date must be in the future")
    @JsonProperty("scheduleDate")
    private LocalDateTime scheduleDate;

    @JsonProperty("practice")
    private boolean practice = false;
}
