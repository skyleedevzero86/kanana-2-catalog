package com.sleekydz86.kanana.domain.benchmark;

import java.util.Objects;

public final class BenchmarkResult {

    private final String modelId;
    private final String benchmarkName;
    private final String metric;
    private final int shot;
    private final double value;
    private final BenchmarkCategory category;

    public BenchmarkResult(String modelId, String benchmarkName, String metric, int shot,
                           double value, BenchmarkCategory category) {
        this.modelId = Objects.requireNonNull(modelId);
        this.benchmarkName = Objects.requireNonNull(benchmarkName);
        this.metric = metric != null ? metric : "acc";
        this.shot = shot;
        this.value = value;
        this.category = category != null ? category : BenchmarkCategory.GENERAL;
    }

    public String getModelId() { return modelId; }
    public String getBenchmarkName() { return benchmarkName; }
    public String getMetric() { return metric; }
    public int getShot() { return shot; }
    public double getValue() { return value; }
    public BenchmarkCategory getCategory() { return category; }
}
