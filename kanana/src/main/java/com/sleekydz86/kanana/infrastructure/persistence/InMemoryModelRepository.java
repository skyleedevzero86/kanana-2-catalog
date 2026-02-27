package com.sleekydz86.kanana.infrastructure.persistence;

import com.sleekydz86.kanana.application.port.ModelRepositoryPort;
import com.sleekydz86.kanana.domain.model.KananaModel;
import com.sleekydz86.kanana.domain.model.ModelSpec;
import com.sleekydz86.kanana.domain.model.ModelVariant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryModelRepository implements ModelRepositoryPort {

    private static final ModelSpec KANANA_2_30B_SPEC = ModelSpec.builder()
            .totalParameters("30B")
            .activatedParameters("3B")
            .layers(48)
            .experts(128)
            .selectedExperts(6)
            .attentionMechanism("MLA")
            .vocabularySize(128_256)
            .contextLength(32_768)
            .build();

    private static final List<String> SIX_LANGUAGES = List.of("ko", "en", "ja", "zh", "th", "vi");

    private final List<KananaModel> store = new CopyOnWriteArrayList<>(List.of(
            KananaModel.builder()
                    .id("kanana-2-30b-a3b-base-2601")
                    .name("kanana-2-30b-a3b-base-2601")
                    .variant(ModelVariant.BASE)
                    .spec(KANANA_2_30B_SPEC)
                    .huggingFaceUrl("https://huggingface.co/kakaocorp/kanana-2-30b-a3b-base-2601")
                    .supportedLanguages(SIX_LANGUAGES)
                    .build(),
            KananaModel.builder()
                    .id("kanana-2-30b-a3b-mid-2601")
                    .name("kanana-2-30b-a3b-mid-2601")
                    .variant(ModelVariant.MID)
                    .spec(KANANA_2_30B_SPEC)
                    .huggingFaceUrl("https://huggingface.co/kakaocorp/kanana-2-30b-a3b-mid-2601")
                    .supportedLanguages(SIX_LANGUAGES)
                    .build(),
            KananaModel.builder()
                    .id("kanana-2-30b-a3b-instruct-2601")
                    .name("kanana-2-30b-a3b-instruct-2601")
                    .variant(ModelVariant.INSTRUCT)
                    .spec(KANANA_2_30B_SPEC)
                    .huggingFaceUrl("https://huggingface.co/kakaocorp/kanana-2-30b-a3b-instruct-2601")
                    .supportedLanguages(SIX_LANGUAGES)
                    .build(),
            KananaModel.builder()
                    .id("kanana-2-30b-a3b-thinking-2601")
                    .name("kanana-2-30b-a3b-thinking-2601")
                    .variant(ModelVariant.THINKING)
                    .spec(KANANA_2_30B_SPEC)
                    .huggingFaceUrl("https://huggingface.co/kakaocorp/kanana-2-30b-a3b-thinking-2601")
                    .supportedLanguages(SIX_LANGUAGES)
                    .build()
    ));

    @Override
    public List<KananaModel> findAll() {
        return List.copyOf(store);
    }

    @Override
    public Optional<KananaModel> findById(String id) {
        return store.stream().filter(m -> m.getId().equals(id)).findFirst();
    }
}
