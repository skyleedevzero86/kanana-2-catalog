package com.sleekydz86.kanana.interfaces.dto;

import jakarta.validation.constraints.NotBlank;

public record CompleteRequestDto(
        String modelId,
        @NotBlank(message = "메시지는 필수입니다.") String message
) {}
