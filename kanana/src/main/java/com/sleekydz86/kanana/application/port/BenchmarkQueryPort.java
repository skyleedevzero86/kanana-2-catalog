package com.sleekydz86.kanana.application.port;

import com.sleekydz86.kanana.domain.benchmark.BenchmarkCategory;
import com.sleekydz86.kanana.domain.benchmark.BenchmarkResult;
import java.util.List;

public interface BenchmarkQueryPort {

    List<BenchmarkResult> findByModelId(String modelId);

    List<BenchmarkResult> findByModelIdAndCategory(String modelId, BenchmarkCategory category);
}
