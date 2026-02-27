package com.sleekydz86.kanana.global.serving;

import com.sleekydz86.kanana.application.port.ServingPresetPort;
import com.sleekydz86.kanana.domain.serving.ServingPreset;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ServingPresetProvider implements ServingPresetPort {

    private final Map<String, ServingPreset> presets = new ConcurrentHashMap<>(Map.of(
            "kanana-2-30b-a3b-instruct-2601",
            ServingPreset.builder()
                    .modelId("kanana-2-30b-a3b-instruct-2601")
                    .commandTemplate("vllm serve kakaocorp/kanana-2-30b-a3b-instruct-2601 --enable-auto-tool-choice --tool-call-parser hermes")
                    .sglangCommandTemplate("python3 -m sglang.launch_server --model-path kakaocorp/kanana-2-30b-a3b-instruct-2601 --tool-call-parser qwen")
                    .toolCallParser("hermes")
                    .minGpuMemoryMb(60_000L)
                    .build(),
            "kanana-2-30b-a3b-thinking-2601",
            ServingPreset.builder()
                    .modelId("kanana-2-30b-a3b-thinking-2601")
                    .commandTemplate("vllm serve kakaocorp/kanana-2-30b-a3b-thinking-2601 --reasoning-parser deepseek_r1 --enable-auto-tool-choice --tool-call-parser hermes")
                    .sglangCommandTemplate("python3 -m sglang.launch_server --model-path kakaocorp/kanana-2-30b-a3b-thinking-2601 --reasoning-parser deepseek-r1 --tool-call-parser qwen")
                    .reasoningParser("deepseek_r1")
                    .toolCallParser("hermes")
                    .minGpuMemoryMb(60_000L)
                    .build()
    ));

    @Override
    public Optional<ServingPreset> findByModelId(String modelId) {
        return Optional.ofNullable(presets.get(modelId));
    }
}
