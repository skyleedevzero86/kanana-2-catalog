package com.sleekydz86.kanana.global.config;

import com.sleekydz86.kanana.application.port.BenchmarkQueryPort;
import com.sleekydz86.kanana.application.port.LlmInferencePort;
import com.sleekydz86.kanana.application.port.ModelRepositoryPort;
import com.sleekydz86.kanana.application.port.ServingPresetPort;
import com.sleekydz86.kanana.application.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

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
    public LlmCompleteUseCase llmCompleteUseCase(LlmInferencePort llmInferencePort) {
        return new LlmCompleteUseCase(llmInferencePort);
    }
}
