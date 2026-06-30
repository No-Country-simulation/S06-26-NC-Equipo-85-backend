package com.appbit.mentorship.dto;

import com.appbit.common.enums.SessionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class MentorshipSummaryResponse {

    private UUID id;

    @JsonProperty("mentor_profile_id")
    private UUID mentorProfileId;

    @JsonProperty("mentee_profile_id")
    private UUID menteeProfileId;

    @JsonProperty("schedule_date")
    private OffsetDateTime scheduleDate;

    private SessionStatus status;

    @JsonProperty("is_practice_invitation")
    private Boolean isPracticeInvitation;
}
