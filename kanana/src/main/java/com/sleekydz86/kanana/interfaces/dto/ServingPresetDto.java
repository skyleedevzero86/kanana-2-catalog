package com.sleekydz86.kanana.interfaces.dto;

import com.sleekydz86.kanana.domain.serving.ServingPreset;

public record ServingPresetDto(
        String modelId,
        String backend,
        String commandTemplate,
        String sglangCommandTemplate,
        String reasoningParser,
        String toolCallParser,
        long minGpuMemoryMb
) {
    public static ServingPresetDto from(ServingPreset p) {
        return new ServingPresetDto(
                p.getModelId(),
                p.getBackend().name(),
                p.getCommandTemplate(),
                p.getSglangCommandTemplate() != null ? p.getSglangCommandTemplate() : "",
                p.getReasoningParser() != null ? p.getReasoningParser() : "",
                p.getToolCallParser(),
                p.getMinGpuMemoryMb()
        );
    }
}
