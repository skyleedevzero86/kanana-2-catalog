package com.sleekydz86.kanana.global.exception;

public class LlmInferenceException extends RuntimeException {

    public LlmInferenceException(String message) {
        super(message);
    }

    public LlmInferenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
