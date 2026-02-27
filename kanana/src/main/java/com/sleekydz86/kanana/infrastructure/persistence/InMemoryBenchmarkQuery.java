package com.sleekydz86.kanana.infrastructure.persistence;

import com.sleekydz86.kanana.application.port.BenchmarkQueryPort;
import com.sleekydz86.kanana.domain.benchmark.BenchmarkCategory;
import com.sleekydz86.kanana.domain.benchmark.BenchmarkResult;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class InMemoryBenchmarkQuery implements BenchmarkQueryPort {

    private static final List<BenchmarkResult> SEED = List.of(
            br("kanana-2-30b-a3b-instruct-2601", "MT-Bench", "judge", 0, 8.30, BenchmarkCategory.CHAT),
            br("kanana-2-30b-a3b-instruct-2601", "KoMT-Bench", "judge", 0, 8.21, BenchmarkCategory.CHAT),
            br("kanana-2-30b-a3b-instruct-2601", "IFEval", "prompt strict", 0, 87.25, BenchmarkCategory.INSTRUCTION_FOLLOWING),
            br("kanana-2-30b-a3b-instruct-2601", "IFBench", "prompt strict", 0, 48.30, BenchmarkCategory.INSTRUCTION_FOLLOWING),
            br("kanana-2-30b-a3b-instruct-2601", "GSM8K", "em", 8, 93.10, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-instruct-2601", "MATH", "acc", 4, 88.56, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-instruct-2601", "HumanEval+", "pass@1", 0, 81.10, BenchmarkCategory.CODING),
            br("kanana-2-30b-a3b-instruct-2601", "MMLU", "em", 5, 81.61, BenchmarkCategory.REASONING_KNOWLEDGE),
            br("kanana-2-30b-a3b-thinking-2601", "MMLU-Pro", "pass@1", 0, 74.2, BenchmarkCategory.REASONING_KNOWLEDGE),
            br("kanana-2-30b-a3b-thinking-2601", "AIME 2025", "pass@1", 0, 74.0, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-thinking-2601", "IFEval", "prompt strict", 0, 82.2, BenchmarkCategory.INSTRUCTION_FOLLOWING)
    );

    private static BenchmarkResult br(String modelId, String name, String metric, int shot,
                                      double value, BenchmarkCategory cat) {
        return new BenchmarkResult(modelId, name, metric, shot, value, cat);
    }

    @Override
    public List<BenchmarkResult> findByModelId(String modelId) {
        return SEED.stream().filter(b -> b.getModelId().equals(modelId)).toList();
    }

    @Override
    public List<BenchmarkResult> findByModelIdAndCategory(String modelId, BenchmarkCategory category) {
        return SEED.stream()
                .filter(b -> b.getModelId().equals(modelId) && b.getCategory() == category)
                .toList();
    }
}
