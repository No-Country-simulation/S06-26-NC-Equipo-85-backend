package com.appbit.course.dto;

import java.util.UUID;

public record CourseResponse(UUID id,
                             String name,
                             String provider) {
}
