package com.sleekydz86.kanana.application.usecase;

import com.sleekydz86.kanana.application.port.ModelRepositoryPort;
import com.sleekydz86.kanana.domain.model.KananaModel;
import java.util.Optional;

public class GetModelByIdUseCase {

    private final ModelRepositoryPort modelRepository;

    public GetModelByIdUseCase(ModelRepositoryPort modelRepository) {
        this.modelRepository = modelRepository;
    }

    public Optional<KananaModel> execute(String id) {
        return modelRepository.findById(id);
    }
}
