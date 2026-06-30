package com.appbit.health.dto;

import com.appbit.common.enums.MoodEmoji;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class CheckinSummaryResponse {

    private UUID id;
    private MoodEmoji emoji;
    private Integer rating;
    private String context;

    @JsonProperty("suggested_action")
    private String suggestedAction;

    @JsonProperty("derive_cvv")
    private Boolean deriveCvv;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;
}
