package com.appbit.job.dto;

import java.util.List;
import java.util.UUID;

public record JobDetailResponse(UUID id,
                                String company,
                                String title,
                                String description,
                                List<String> requiredSkills) {
}
