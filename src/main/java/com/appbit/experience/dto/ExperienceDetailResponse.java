package com.appbit.experience.dto;

import com.appbit.common.enums.ExperienceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ExperienceDetailResponse {

    private UUID id;

    private String title;
    
    @JsonProperty("mentor_profile_id")
    private UUID mentorProfileId;

    private boolean owner;

    private String description;

    @JsonProperty("speaker_name")
    private String speakerName;

    @JsonProperty("speaker_role")
    private String speakerRole;

    private ExperienceType type;

    @JsonProperty("content_url")
    private String contentUrl;

    @JsonProperty("date_time")
    private ZonedDateTime dateTime;

    private List<SkillSummaryResponse> skills;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
