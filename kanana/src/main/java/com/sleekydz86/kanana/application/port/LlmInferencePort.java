package com.sleekydz86.kanana.application.port;

public interface LlmInferencePort {
    String complete(String modelId, String message);
}
