package com.sleekydz86.kanana.application.usecase;

import com.sleekydz86.kanana.application.port.LlmInferencePort;

public class LlmCompleteUseCase {

    private final LlmInferencePort llmInferencePort;

    public LlmCompleteUseCase(LlmInferencePort llmInferencePort) {
        this.llmInferencePort = llmInferencePort;
    }

    public String execute(String modelId, String message) {
        return llmInferencePort.complete(modelId, message);
    }
}
