package com.sleekydz86.kanana.domain.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class KananaModel {

    private final String id;
    private final String name;
    private final ModelVariant variant;
    private final ModelSpec spec;
    private final String huggingFaceUrl;
    private final List<String> supportedLanguages;

    private KananaModel(Builder b) {
        this.id = b.id;
        this.name = b.name;
        this.variant = b.variant;
        this.spec = b.spec;
        this.huggingFaceUrl = b.huggingFaceUrl;
        this.supportedLanguages = b.supportedLanguages == null
                ? List.of("ko", "en")
                : Collections.unmodifiableList(new ArrayList<>(b.supportedLanguages));
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public ModelVariant getVariant() { return variant; }
    public ModelSpec getSpec() { return spec; }
    public String getHuggingFaceUrl() { return huggingFaceUrl; }
    public List<String> getSupportedLanguages() { return supportedLanguages; }

    public boolean supportsToolCalling() {
        return variant == ModelVariant.INSTRUCT || variant == ModelVariant.THINKING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KananaModel that = (KananaModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static final class Builder {
        private String id;
        private String name;
        private ModelVariant variant;
        private ModelSpec spec;
        private String huggingFaceUrl;
        private List<String> supportedLanguages;

        public Builder id(String v) { id = v; return this; }
        public Builder name(String v) { name = v; return this; }
        public Builder variant(ModelVariant v) { variant = v; return this; }
        public Builder spec(ModelSpec v) { spec = v; return this; }
        public Builder huggingFaceUrl(String v) { huggingFaceUrl = v; return this; }
        public Builder supportedLanguages(List<String> v) { supportedLanguages = v; return this; }

        public KananaModel build() {
            Objects.requireNonNull(id, "id");
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(variant, "variant");
            Objects.requireNonNull(spec, "spec");
            return new KananaModel(this);
        }
    }
}
