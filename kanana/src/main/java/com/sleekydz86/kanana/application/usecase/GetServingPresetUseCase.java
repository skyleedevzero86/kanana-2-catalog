package com.sleekydz86.kanana.application.usecase;

import com.sleekydz86.kanana.application.port.ServingPresetPort;

import java.util.Optional;

public class GetServingPresetUseCase {

    private final ServingPresetPort servingPresetPort;

    public GetServingPresetUseCase(ServingPresetPort servingPresetPort) {
        this.servingPresetPort = servingPresetPort;
    }

    public Optional<ServingPreset> execute(String modelId) {
        return servingPresetPort.findByModelId(modelId);
    }
}
