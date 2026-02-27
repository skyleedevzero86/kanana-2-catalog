package com.sleekydz86.kanana.interfaces.dto;

import com.sleekydz86.kanana.domain.model.ModelSpec;

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
    public static ModelSpecDto from(ModelSpec s) {
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
