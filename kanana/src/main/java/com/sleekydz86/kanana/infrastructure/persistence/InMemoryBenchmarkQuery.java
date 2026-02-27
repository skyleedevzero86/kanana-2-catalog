package com.sleekydz86.kanana.infrastructure.persistence;

import com.sleekydz86.kanana.application.port.BenchmarkQueryPort;
import com.sleekydz86.kanana.domain.benchmark.BenchmarkCategory;
import com.sleekydz86.kanana.domain.benchmark.BenchmarkResult;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class InMemoryBenchmarkQuery implements BenchmarkQueryPort {

    private static final List<BenchmarkResult> SEED = List.of(

            br("kanana-2-30b-a3b-base-2601", "MMLU",        "acc",    5, 74.83, BenchmarkCategory.GENERAL),
            br("kanana-2-30b-a3b-base-2601", "MMLU-Pro",    "acc",    5, 52.61, BenchmarkCategory.GENERAL),
            br("kanana-2-30b-a3b-base-2601", "BBH",         "acc",    3, 76.46, BenchmarkCategory.GENERAL),
            br("kanana-2-30b-a3b-base-2601", "SimpleQA",    "acc",    5, 29.13, BenchmarkCategory.GENERAL),
            br("kanana-2-30b-a3b-base-2601", "MATH",        "em",     4, 48.86, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-base-2601", "GSM8K",       "em",     8, 76.57, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-base-2601", "HumanEval",   "pass@1", 0, 71.34, BenchmarkCategory.CODING),
            br("kanana-2-30b-a3b-base-2601", "MBPP",        "pass@1", 3, 60.21, BenchmarkCategory.CODING),
            br("kanana-2-30b-a3b-base-2601", "KMMLU",       "acc",    5, 61.98, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-base-2601", "KoSimpleQA",  "acc",    5, 49.40, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-base-2601", "HAE-RAE Bench","acc",   5, 88.91, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-base-2601", "MATH-Ko",     "em",     4, 45.58, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-base-2601", "GSM8K-Ko",    "em",     8, 70.43, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-base-2601", "MBPP-Ko",     "pass@1", 3, 57.29, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-base-2601", "RULER-4K",    "acc",    0, 92.49, BenchmarkCategory.LONG_CONTEXT),
            br("kanana-2-30b-a3b-base-2601", "RULER-8K",    "acc",    0, 92.14, BenchmarkCategory.LONG_CONTEXT),
            br("kanana-2-30b-a3b-base-2601", "RULER-16K",   "acc",    0, 90.01, BenchmarkCategory.LONG_CONTEXT),
            br("kanana-2-30b-a3b-base-2601", "RULER-32K",   "acc",    0, 87.92, BenchmarkCategory.LONG_CONTEXT),

            br("kanana-2-30b-a3b-mid-2601", "MMLU",         "acc",    5, 75.44, BenchmarkCategory.GENERAL),
            br("kanana-2-30b-a3b-mid-2601", "MMLU-Pro",     "acc",    5, 56.14, BenchmarkCategory.GENERAL),
            br("kanana-2-30b-a3b-mid-2601", "BBH",          "acc",    3, 79.76, BenchmarkCategory.GENERAL),
            br("kanana-2-30b-a3b-mid-2601", "SimpleQA",     "acc",    5, 29.70, BenchmarkCategory.GENERAL),
            br("kanana-2-30b-a3b-mid-2601", "MATH",         "em",     4, 54.40, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-mid-2601", "GSM8K",        "em",     8, 82.71, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-mid-2601", "HumanEval",    "pass@1", 0, 75.29, BenchmarkCategory.CODING),
            br("kanana-2-30b-a3b-mid-2601", "MBPP",         "pass@1", 3, 62.39, BenchmarkCategory.CODING),
            br("kanana-2-30b-a3b-mid-2601", "KMMLU",        "acc",    5, 62.15, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-mid-2601", "KoSimpleQA",   "acc",    5, 49.70, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-mid-2601", "HAE-RAE Bench","acc",    5, 88.73, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-mid-2601", "MATH-Ko",      "em",     4, 54.07, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-mid-2601", "GSM8K-Ko",     "em",     8, 77.48, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-mid-2601", "MBPP-Ko",      "pass@1", 3, 61.55, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-mid-2601", "RULER-4K",     "acc",    0, 93.09, BenchmarkCategory.LONG_CONTEXT),
            br("kanana-2-30b-a3b-mid-2601", "RULER-8K",     "acc",    0, 92.29, BenchmarkCategory.LONG_CONTEXT),
            br("kanana-2-30b-a3b-mid-2601", "RULER-16K",    "acc",    0, 90.73, BenchmarkCategory.LONG_CONTEXT),
            br("kanana-2-30b-a3b-mid-2601", "RULER-32K",    "acc",    0, 88.63, BenchmarkCategory.LONG_CONTEXT),

            br("kanana-2-30b-a3b-instruct-2601", "MT-Bench",          "judge",        0, 8.30,  BenchmarkCategory.CHAT),
            br("kanana-2-30b-a3b-instruct-2601", "KoMT-Bench",        "judge",        0, 8.21,  BenchmarkCategory.CHAT),
            br("kanana-2-30b-a3b-instruct-2601", "IFEval",            "prompt strict",0, 87.25, BenchmarkCategory.INSTRUCTION_FOLLOWING),
            br("kanana-2-30b-a3b-instruct-2601", "IFBench",           "prompt strict",0, 48.30, BenchmarkCategory.INSTRUCTION_FOLLOWING),
            br("kanana-2-30b-a3b-instruct-2601", "Multi-IF (EN)",     "acc",          0, 77.88, BenchmarkCategory.INSTRUCTION_FOLLOWING),
            br("kanana-2-30b-a3b-instruct-2601", "Multi-Challenge",   "acc",          0, 35.16, BenchmarkCategory.INSTRUCTION_FOLLOWING),
            br("kanana-2-30b-a3b-instruct-2601", "BFCL-v3 Live",      "pass@1",       0, 76.66, BenchmarkCategory.TOOL_CALLING),
            br("kanana-2-30b-a3b-instruct-2601", "BFCL-v3 Multi-Turn","pass@1",       0, 38.63, BenchmarkCategory.TOOL_CALLING),
            br("kanana-2-30b-a3b-instruct-2601", "HumanEval+",        "pass@1",       0, 81.10, BenchmarkCategory.CODING),
            br("kanana-2-30b-a3b-instruct-2601", "MBPP+",             "pass@1",       0, 73.02, BenchmarkCategory.CODING),
            br("kanana-2-30b-a3b-instruct-2601", "GSM8K",             "em",           0, 93.10, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-instruct-2601", "MATH",              "acc",          0, 88.56, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-instruct-2601", "MMLU",              "em",           5, 81.61, BenchmarkCategory.REASONING_KNOWLEDGE),
            br("kanana-2-30b-a3b-instruct-2601", "KMMLU",             "em",           0, 68.26, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-instruct-2601", "GPQA Diamond",      "pass@1",       0, 52.53, BenchmarkCategory.REASONING_KNOWLEDGE),
            br("kanana-2-30b-a3b-instruct-2601", "HAERAE-Bench",      "em",           0, 75.57, BenchmarkCategory.KOREAN),

            br("kanana-2-30b-a3b-thinking-2601", "MMLU-Pro",          "pass@1",       0, 74.2, BenchmarkCategory.REASONING_KNOWLEDGE),
            br("kanana-2-30b-a3b-thinking-2601", "GPQA Diamond",      "pass@1",       0, 57.8, BenchmarkCategory.REASONING_KNOWLEDGE),
            br("kanana-2-30b-a3b-thinking-2601", "AIME 2025",         "pass@1",       0, 74.0, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-thinking-2601", "AIME 2024",         "pass@1",       0, 79.0, BenchmarkCategory.MATHEMATICS),
            br("kanana-2-30b-a3b-thinking-2601", "AIME 2024-Ko",      "pass@1",       0, 75.0, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-thinking-2601", "LiveCodeBench",     "pass@1",       0, 58.8, BenchmarkCategory.CODING),
            br("kanana-2-30b-a3b-thinking-2601", "LiveCodeBench-Ko",  "pass@1",       0, 51.2, BenchmarkCategory.KOREAN),
            br("kanana-2-30b-a3b-thinking-2601", "IFEval",            "prompt strict",0, 82.2, BenchmarkCategory.INSTRUCTION_FOLLOWING),
            br("kanana-2-30b-a3b-thinking-2601", "IFBench",           "prompt strict",0, 47.8, BenchmarkCategory.INSTRUCTION_FOLLOWING),
            br("kanana-2-30b-a3b-thinking-2601", "BFCL-v3 Live",      "pass@1",       0, 75.9, BenchmarkCategory.TOOL_CALLING),
            br("kanana-2-30b-a3b-thinking-2601", "BFCL-v3 Multi-Turn","pass@1",       0, 43.7, BenchmarkCategory.TOOL_CALLING)
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
