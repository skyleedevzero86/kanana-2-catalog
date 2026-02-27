package com.sleekydz86.kanana.interfaces.web;

import com.sleekydz86.kanana.application.usecase.GetServingPresetUseCase;
import com.sleekydz86.kanana.interfaces.dto.ServingPresetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/models/{modelId}/serving")
public class ServingController {

    private final GetServingPresetUseCase getServingPresetUseCase;

    public ServingController(GetServingPresetUseCase getServingPresetUseCase) {
        this.getServingPresetUseCase = getServingPresetUseCase;
    }

    @GetMapping
    public ResponseEntity<ServingPresetDto> getPreset(@PathVariable String modelId) {
        return getServingPresetUseCase.execute(modelId)
                .map(ServingPresetDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
