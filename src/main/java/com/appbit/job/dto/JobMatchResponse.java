package com.appbit.job.dto;

import java.util.UUID;

public record JobMatchResponse(
        UUID jobId,
        String company,
        String title,
        double matchRate
) {
}
