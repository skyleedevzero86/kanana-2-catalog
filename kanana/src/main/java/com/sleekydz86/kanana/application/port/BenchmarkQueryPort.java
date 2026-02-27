package com.sleekydz86.kanana.application.port;

import java.util.List;

public interface BenchmarkQueryPort {

    List<BenchmarkResult> findByModelId(String modelId);

    List<BenchmarkResult> findByModelIdAndCategory(String modelId, BenchmarkCategory category);
}
