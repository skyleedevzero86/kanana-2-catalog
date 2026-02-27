package com.sleekydz86.kanana.domain.serving;

import java.util.Objects;

public final class ServingPreset {

    private final String modelId;
    private final Backend backend;
    private final String commandTemplate;
    private final String reasoningParser;
    private final String toolCallParser;
    private final long minGpuMemoryMb;

    private ServingPreset(Builder b) {
        this.modelId = b.modelId;
        this.backend = b.backend;
        this.commandTemplate = b.commandTemplate;
        this.reasoningParser = b.reasoningParser;
        this.toolCallParser = b.toolCallParser;
        this.minGpuMemoryMb = b.minGpuMemoryMb;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getModelId() { return modelId; }
    public Backend getBackend() { return backend; }
    public String getCommandTemplate() { return commandTemplate; }
    public String getReasoningParser() { return reasoningParser; }
    public String getToolCallParser() { return toolCallParser; }
    public long getMinGpuMemoryMb() { return minGpuMemoryMb; }

    public static final class Builder {
        private String modelId;
        private Backend backend = Backend.VLLM;
        private String commandTemplate;
        private String reasoningParser;
        private String toolCallParser = "hermes";
        private long minGpuMemoryMb = 60_000L;

        public Builder modelId(String v) { modelId = v; return this; }
        public Builder backend(Backend v) { backend = v; return this; }
        public Builder commandTemplate(String v) { commandTemplate = v; return this; }
        public Builder reasoningParser(String v) { reasoningParser = v; return this; }
        public Builder toolCallParser(String v) { toolCallParser = v; return this; }
        public Builder minGpuMemoryMb(long v) { minGpuMemoryMb = v; return this; }

        public ServingPreset build() {
            Objects.requireNonNull(modelId);
            Objects.requireNonNull(commandTemplate);
            return new ServingPreset(this);
        }
    }
}
