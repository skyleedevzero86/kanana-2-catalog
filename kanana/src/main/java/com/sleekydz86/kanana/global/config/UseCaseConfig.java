package com.sleekydz86.kanana.global.config;

import com.sleekydz86.kanana.application.port.BenchmarkQueryPort;
import com.sleekydz86.kanana.application.port.LlmInferencePort;
import com.sleekydz86.kanana.application.port.ModelRepositoryPort;
import com.sleekydz86.kanana.application.port.ServingPresetPort;
import com.sleekydz86.kanana.application.usecase.*;
import com.sleekydz86.kanana.global.gateway.MockLlmInferenceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private static final Logger log = LoggerFactory.getLogger(UseCaseConfig.class);

    @Bean
    public GetModelCatalogUseCase getModelCatalogUseCase(ModelRepositoryPort modelRepository) {
        return new GetModelCatalogUseCase(modelRepository);
    }

    @Bean
    public GetModelByIdUseCase getModelByIdUseCase(ModelRepositoryPort modelRepository) {
        return new GetModelByIdUseCase(modelRepository);
    }

    @Bean
    public GetBenchmarksForModelUseCase getBenchmarksForModelUseCase(BenchmarkQueryPort benchmarkQueryPort) {
        return new GetBenchmarksForModelUseCase(benchmarkQueryPort);
    }

    @Bean
    public GetServingPresetUseCase getServingPresetUseCase(ServingPresetPort servingPresetPort) {
        return new GetServingPresetUseCase(servingPresetPort);
    }

    @Bean
    @ConditionalOnProperty(name = "app.llm.mock-enabled", havingValue = "true")
    public LlmInferencePort mockLlmInferencePort() {
        log.info("LLM Mock 모드 활성화 — vLLM 서버 없이 테스트합니다.");
        return new MockLlmInferenceAdapter();
    }

    @Bean
    public LlmCompleteUseCase llmCompleteUseCase(LlmInferencePort llmInferencePort) {
        return new LlmCompleteUseCase(llmInferencePort);
    }
}
