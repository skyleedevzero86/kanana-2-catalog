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

    public record ModelSpecDto(
            String totalParameters,
            String activatedParameters,
            int layers,
            int experts,
            int selectedExperts,
            String attentionMechanism,
            int vocabularySize,
            int contextLength
    ) {
        static ModelSpecDto from(com.sleekydz86.kanana.domain.model.ModelSpec s) {
            return new ModelSpecDto(
                    s.getTotalParameters(),
                    s.getActivatedParameters(),
                    s.getLayers(),
                    s.getExperts(),
                    s.getSelectedExperts(),
                    s.getAttentionMechanism(),
                    s.getVocabularySize(),
                    s.getContextLength()
            );
        }
    }
}
