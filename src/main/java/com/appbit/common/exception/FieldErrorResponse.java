package com.appbit.common.exception;

public record FieldErrorResponse(
        String field,
        String message
) {
}
