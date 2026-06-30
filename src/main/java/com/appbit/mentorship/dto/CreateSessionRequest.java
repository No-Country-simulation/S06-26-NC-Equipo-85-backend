package com.appbit.mentorship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSessionRequest {

    @NotNull(message = "Schedule date is required")
    @Future(message = "Schedule date must be in the future")
    @JsonProperty("scheduleDate")
    private LocalDateTime scheduleDate;

    @JsonProperty("practice")
    private boolean practice = false;
}
