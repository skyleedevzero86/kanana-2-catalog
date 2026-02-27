package com.sleekydz86.kanana.application.port;

public interface LlmInferencePort {
    CompletionResult complete(String modelId, String message);
}
