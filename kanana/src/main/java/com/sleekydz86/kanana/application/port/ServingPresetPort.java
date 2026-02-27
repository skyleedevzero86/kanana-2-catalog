package com.sleekydz86.kanana.application.port;

import com.sleekydz86.kanana.domain.serving.ServingPreset;
import java.util.Optional;

public interface ServingPresetPort {

    Optional<ServingPreset> findByModelId(String modelId);
}
