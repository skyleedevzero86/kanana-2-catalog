package com.sleekydz86.kanana.interfaces.web;

import com.sleekydz86.kanana.application.usecase.LlmCompleteUseCase;
import com.sleekydz86.kanana.interfaces.dto.CompleteRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/complete")
public class CompleteController {

    private final LlmCompleteUseCase llmCompleteUseCase;

    public CompleteController(LlmCompleteUseCase llmCompleteUseCase) {
        this.llmCompleteUseCase = llmCompleteUseCase;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> complete(@Valid @RequestBody CompleteRequestDto dto) {
        String modelId = dto.modelId() != null && !dto.modelId().isBlank() ? dto.modelId() : null;
        String text = llmCompleteUseCase.execute(modelId, dto.message());
        return ResponseEntity.ok(Map.of("content", text != null ? text : ""));
    }
}
