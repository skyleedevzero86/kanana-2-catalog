package com.sleekydz86.kanana.interfaces.dto;

import com.sleekydz86.kanana.domain.model.ModelSpec;

public record ModelSpecDto(
        String totalParameters,
        String activatedParameters,
        int layers,
        int denseLayers,
        int experts,
        int selectedExperts,
        int sharedExperts,
        String attentionMechanism,
        int vocabularySize,
        int contextLength
) {
    public static ModelSpecDto from(ModelSpec s) {
        return new ModelSpecDto(
                s.getTotalParameters(),
                s.getActivatedParameters(),
                s.getLayers(),
                s.getDenseLayers(),
                s.getExperts(),
                s.getSelectedExperts(),
                s.getSharedExperts(),
                s.getAttentionMechanism(),
                s.getVocabularySize(),
                s.getContextLength()
        );
    }
}
