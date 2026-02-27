package com.sleekydz86.kanana.application.usecase;

import com.sleekydz86.kanana.application.port.ModelRepositoryPort;

import java.util.List;

public class GetModelCatalogUseCase {

    private final ModelRepositoryPort modelRepository;

    public GetModelCatalogUseCase(ModelRepositoryPort modelRepository) {
        this.modelRepository = modelRepository;
    }

    public List<KananaModel> execute() {
        return modelRepository.findAll();
    }
}
