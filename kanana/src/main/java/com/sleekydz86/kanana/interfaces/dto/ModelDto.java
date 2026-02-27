package com.sleekydz86.kanana.interfaces.dto;

import com.sleekydz86.kanana.domain.model.KananaModel;

import java.util.List;

public record ModelDto(
        String id,
        String name,
        String variant,
        ModelSpecDto spec,
        String huggingFaceUrl,
        List<String> supportedLanguages,
        boolean supportsToolCalling
) {
    public static ModelDto from(KananaModel m) {
        return new ModelDto(
                m.getId(),
                m.getName(),
                m.getVariant().name(),
                ModelSpecDto.from(m.getSpec()),
                m.getHuggingFaceUrl(),
                m.getSupportedLanguages(),
                m.supportsToolCalling()
        );
    }
}
