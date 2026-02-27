package com.sleekydz86.kanana.interfaces.web;

import com.sleekydz86.kanana.application.usecase.GetBenchmarksForModelUseCase;
import com.sleekydz86.kanana.domain.benchmark.BenchmarkCategory;
import com.sleekydz86.kanana.domain.benchmark.BenchmarkResult;
import com.sleekydz86.kanana.interfaces.dto.BenchmarkDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/models/{modelId}/benchmarks")
public class BenchmarkController {

    private final GetBenchmarksForModelUseCase getBenchmarksForModelUseCase;

    public BenchmarkController(GetBenchmarksForModelUseCase getBenchmarksForModelUseCase) {
        this.getBenchmarksForModelUseCase = getBenchmarksForModelUseCase;
    }

    @GetMapping
    public List<BenchmarkDto> list(@PathVariable String modelId,
                                   @RequestParam(required = false) BenchmarkCategory category) {
        List<BenchmarkResult> results = category == null
                ? getBenchmarksForModelUseCase.execute(modelId)
                : getBenchmarksForModelUseCase.execute(modelId, category);
        return results.stream().map(BenchmarkDto::from).toList();
    }
}
