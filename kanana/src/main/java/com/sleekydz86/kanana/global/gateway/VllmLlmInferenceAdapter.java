package com.sleekydz86.kanana.global.gateway;

import com.sleekydz86.kanana.application.port.LlmInferencePort;
import com.sleekydz86.kanana.global.exception.LlmInferenceException;
import com.sleekydz86.kanana.global.gateway.dto.VllmChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "app.llm.mock-enabled", havingValue = "false", matchIfMissing = true)
public class VllmLlmInferenceAdapter implements LlmInferencePort {

    private static final Logger log = LoggerFactory.getLogger(VllmLlmInferenceAdapter.class);

    private final WebClient webClient;
    private final String defaultModelId;
    private final String apiKey;
    private final String completionUrl;

    public VllmLlmInferenceAdapter(
            WebClient.Builder builder,
            @Value("${app.llm.inference-base-url:http://localhost:8000}") String baseUrl,
            @Value("${app.llm.default-model-id:kanana-2-30b-a3b-instruct-2601}") String defaultModelId,
            @Value("${app.llm.api-key:}") String apiKey,
            @Value("${app.llm.completion-path:/v1/chat/completions}") String completionPath) {
        this.webClient = builder.build();
        this.defaultModelId = defaultModelId;
        this.apiKey = apiKey;
        String base = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String path = completionPath.startsWith("/") ? completionPath : "/" + completionPath;
        this.completionUrl = base + path;
        log.info("LLM inference endpoint: {}", this.completionUrl);
    }

    @Override
    public String complete(String modelId, String message) {
        String effectiveModel = resolveModelId(modelId);
        var body = buildRequestBody(effectiveModel, message);
        VllmChatResponse response = sendRequest(body, effectiveModel);
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

    private VllmChatResponse sendRequest(Map<String, Object> body, String model) {
        try {
            var request = webClient.post()
                    .uri(URI.create(completionUrl))
                    .contentType(MediaType.APPLICATION_JSON);

            if (apiKey != null && !apiKey.isBlank()) {
                request = request.header("Authorization", "Bearer " + apiKey);
            }

            return request
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(s -> s.value() == 404,
                            r -> Mono.just(new LlmInferenceException(
                                    "요청한 모델을 찾을 수 없습니다: '" + model + "'. "
                                    + "Kanana 모델은 로컬 vLLM 서버에서만 사용 가능합니다.")))
                    .onStatus(s -> s.value() == 401 || s.value() == 403,
                            r -> Mono.just(new LlmInferenceException(
                                    "LLM 서버 인증에 실패했습니다. API 키를 확인하세요. (HTTP " + r.statusCode().value() + ")")))
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
