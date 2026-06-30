package com.appbit.experience.dto;

import com.appbit.common.enums.ExperienceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ExperienceRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Speaker role is required")
    private String speakerRole;

    @NotNull(message = "Type is required")
    private ExperienceType type;

    @NotBlank(message = "Content URL is required")
    private String contentUrl;

    @NotNull(message = "Date and time is required")
    private ZonedDateTime dateTime;

    @NotEmpty(message = "At least one skill is required")
    private List<UUID> skillIds;
}
