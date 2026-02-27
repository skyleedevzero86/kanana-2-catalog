package com.sleekydz86.kanana.domain.model;


import java.util.Objects;

public final class ModelSpec {

    private final String totalParameters;
    private final String activatedParameters;
    private final int layers;
    private final int experts;
    private final int selectedExperts;
    private final String attentionMechanism;
    private final int vocabularySize;
    private final int contextLength;

    private ModelSpec(Builder b) {
        this.totalParameters = b.totalParameters;
        this.activatedParameters = b.activatedParameters;
        this.layers = b.layers;
        this.experts = b.experts;
        this.selectedExperts = b.selectedExperts;
        this.attentionMechanism = b.attentionMechanism;
        this.vocabularySize = b.vocabularySize;
        this.contextLength = b.contextLength;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTotalParameters() { return totalParameters; }
    public String getActivatedParameters() { return activatedParameters; }
    public int getLayers() { return layers; }
    public int getExperts() { return experts; }
    public int getSelectedExperts() { return selectedExperts; }
    public String getAttentionMechanism() { return attentionMechanism; }
    public int getVocabularySize() { return vocabularySize; }
    public int getContextLength() { return contextLength; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelSpec that = (ModelSpec) o;
        return layers == that.layers && experts == that.experts
                && selectedExperts == that.selectedExperts && vocabularySize == that.vocabularySize
                && contextLength == that.contextLength
                && Objects.equals(totalParameters, that.totalParameters)
                && Objects.equals(activatedParameters, that.activatedParameters)
                && Objects.equals(attentionMechanism, that.attentionMechanism);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalParameters, activatedParameters, layers, experts,
                selectedExperts, attentionMechanism, vocabularySize, contextLength);
    }

    public static final class Builder {
        private String totalParameters = "30B";
        private String activatedParameters = "3B";
        private int layers = 48;
        private int experts = 128;
        private int selectedExperts = 6;
        private String attentionMechanism = "MLA";
        private int vocabularySize = 128_256;
        private int contextLength = 32_768;

        public Builder totalParameters(String v) { totalParameters = v; return this; }
        public Builder activatedParameters(String v) { activatedParameters = v; return this; }
        public Builder layers(int v) { layers = v; return this; }
        public Builder experts(int v) { experts = v; return this; }
        public Builder selectedExperts(int v) { selectedExperts = v; return this; }
        public Builder attentionMechanism(String v) { attentionMechanism = v; return this; }
        public Builder vocabularySize(int v) { vocabularySize = v; return this; }
        public Builder contextLength(int v) { contextLength = v; return this; }

        public ModelSpec build() {
            return new ModelSpec(this);
        }
    }
}
