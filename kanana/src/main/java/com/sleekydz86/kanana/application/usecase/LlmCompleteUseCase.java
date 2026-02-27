package com.sleekydz86.kanana.application.usecase;

import com.sleekydz86.kanana.application.port.CompletionResult;
import com.sleekydz86.kanana.application.port.LlmInferencePort;

public class LlmCompleteUseCase {

    private final LlmInferencePort llmInferencePort;

    public LlmCompleteUseCase(LlmInferencePort llmInferencePort) {
        this.llmInferencePort = llmInferencePort;
    }

    public CompletionResult execute(String modelId, String message) {
        return llmInferencePort.complete(modelId, message);
    }
}
