package com.appbit.experience.dto;

import com.appbit.common.enums.ExperienceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class ExperienceSummaryResponse {

    private UUID id;

    private UUID mentorProfileId;

    private String title;

    @JsonProperty("speaker_name")
    private String speakerName;

    @JsonProperty("speaker_role")
    private String speakerRole;

    private ExperienceType type;

    @JsonProperty("date_time")
    private ZonedDateTime dateTime;

    @JsonProperty("content_url")
    private String contentUrl;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
