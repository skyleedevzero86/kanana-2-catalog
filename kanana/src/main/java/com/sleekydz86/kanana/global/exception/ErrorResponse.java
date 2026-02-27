package com.sleekydz86.kanana.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String message,
        String errorCode,
        List<FieldErrorDto> errors
) {
    public static ErrorResponse of(String message) {
        return new ErrorResponse(message, null, null);
    }

    public static ErrorResponse of(String message, String errorCode) {
        return new ErrorResponse(message, errorCode, null);
    }

    public static ErrorResponse of(String message, List<FieldErrorDto> errors) {
        return new ErrorResponse(message, "VALIDATION_ERROR", errors);
    }

    public record FieldErrorDto(String field, String message) {}
}
