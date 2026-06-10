package com.appbit.common.exception;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ApiErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldErrorResponse> fieldErrors
) {
}
