package com.sleekydz86.kanana.application.usecase;

import com.sleekydz86.kanana.application.port.BenchmarkQueryPort;
import com.sleekydz86.kanana.domain.benchmark.BenchmarkCategory;
import com.sleekydz86.kanana.domain.benchmark.BenchmarkResult;
import java.util.List;

public class GetBenchmarksForModelUseCase {

    private final BenchmarkQueryPort benchmarkQueryPort;

    public GetBenchmarksForModelUseCase(BenchmarkQueryPort benchmarkQueryPort) {
        this.benchmarkQueryPort = benchmarkQueryPort;
    }

    public List<BenchmarkResult> execute(String modelId) {
        return benchmarkQueryPort.findByModelId(modelId);
    }

    public List<BenchmarkResult> execute(String modelId, BenchmarkCategory category) {
        return benchmarkQueryPort.findByModelIdAndCategory(modelId, category);
    }
}
