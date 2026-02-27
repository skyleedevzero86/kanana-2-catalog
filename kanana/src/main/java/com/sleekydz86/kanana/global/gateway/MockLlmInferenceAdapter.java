package com.sleekydz86.kanana.global.gateway;

import com.sleekydz86.kanana.application.port.LlmInferencePort;

public class MockLlmInferenceAdapter implements LlmInferencePort {

    @Override
    public String complete(String modelId, String message) {
        String model = (modelId != null && !modelId.isBlank())
                ? modelId : "kanana-2-30b-a3b-instruct-2601";

        return "[Mock 응답] 모델 '%s'이(가) 메시지를 처리했습니다.\n\n요청: %s\n\n이것은 vLLM 서버 없이 테스트용으로 생성된 Mock 응답입니다."
                .formatted(model, message);
    }
}
