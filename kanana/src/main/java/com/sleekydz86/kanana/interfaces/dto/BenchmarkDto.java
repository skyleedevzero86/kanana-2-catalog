package com.sleekydz86.kanana.interfaces.dto;

import com.sleekydz86.kanana.domain.benchmark.BenchmarkResult;

public record BenchmarkDto(
        String modelId,
        String benchmarkName,
        String metric,
        int shot,
        double value,
        String category
) {
    public static BenchmarkDto from(BenchmarkResult r) {
        return new BenchmarkDto(
                r.getModelId(),
                r.getBenchmarkName(),
                r.getMetric(),
                r.getShot(),
                r.getValue(),
                r.getCategory().name()
        );
    }
}
