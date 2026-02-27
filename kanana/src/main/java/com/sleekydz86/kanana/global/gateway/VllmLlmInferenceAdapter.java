package com.sleekydz86.kanana.global.gateway;

import com.sleekydz86.kanana.application.port.LlmInferencePort;
import com.sleekydz86.kanana.global.exception.LlmInferenceException;
import com.sleekydz86.kanana.global.gateway.dto.VllmChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class VllmLlmInferenceAdapter implements LlmInferencePort {

    private static final Logger log = LoggerFactory.getLogger(VllmLlmInferenceAdapter.class);

    private final WebClient webClient;
    private final String defaultModelId;

    public VllmLlmInferenceAdapter(
            WebClient.Builder builder,
            @Value("${app.llm.inference-base-url:http://localhost:8000}") String baseUrl,
            @Value("${app.llm.default-model-id:kanana-2-30b-a3b-instruct-2601}") String defaultModelId) {
        this.webClient = builder.baseUrl(baseUrl).build();
        this.defaultModelId = defaultModelId;
    }

    @Override
    public String complete(String modelId, String message) {
        String effectiveModel = resolveModelId(modelId);
        var body = buildRequestBody(effectiveModel, message);
        VllmChatResponse response = sendRequest(body);
        return extractContent(response, effectiveModel);
    }

    private String resolveModelId(String modelId) {
        return modelId != null && !modelId.isBlank() ? modelId : defaultModelId;
    }

    private Map<String, Object> buildRequestBody(String model, String message) {
        return Map.of(
                "model", model,
                "messages", List.of(Map.of("role", "user", "content", message)),
                "max_tokens", 512
        );
    }

    private VllmChatResponse sendRequest(Map<String, Object> body) {
        try {
            return webClient.post()
                    .uri("/v1/chat/completions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            r -> Mono.just(new LlmInferenceException(
                                    "LLM 서버 요청이 거부되었습니다. (HTTP " + r.statusCode().value() + ")")))
                    .onStatus(HttpStatusCode::is5xxServerError,
                            r -> Mono.just(new LlmInferenceException(
                                    "LLM 서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요. (HTTP " + r.statusCode().value() + ")")))
                    .bodyToMono(VllmChatResponse.class)
                    .block();
        } catch (LlmInferenceException e) {
            throw e;
        } catch (Exception e) {
            log.error("vLLM 인퍼런스 호출 중 오류 발생: {}", e.getMessage());
            throw new LlmInferenceException("LLM 서버에 연결할 수 없습니다. 서버가 실행 중인지 확인해 주세요.", e);
        }
    }

    private String extractContent(VllmChatResponse response, String model) {
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            log.warn("vLLM 응답에 결과가 없습니다. 모델: {}", model);
            return "";
        }
        var choice = response.getChoices().get(0);
        return choice.getMessage() != null ? choice.getMessage().getContent() : "";
    }
}
