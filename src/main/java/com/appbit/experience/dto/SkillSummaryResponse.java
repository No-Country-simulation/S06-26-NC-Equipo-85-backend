package com.appbit.experience.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SkillSummaryResponse {

    private UUID id;

    private String name;
}
