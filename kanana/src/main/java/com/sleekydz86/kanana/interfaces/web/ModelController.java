package com.sleekydz86.kanana.interfaces.web;

import com.sleekydz86.kanana.application.usecase.GetModelByIdUseCase;
import com.sleekydz86.kanana.application.usecase.GetModelCatalogUseCase;
import com.sleekydz86.kanana.interfaces.dto.ModelDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/models")
public class ModelController {

    private final GetModelCatalogUseCase getModelCatalogUseCase;
    private final GetModelByIdUseCase getModelByIdUseCase;

    public ModelController(GetModelCatalogUseCase getModelCatalogUseCase,
                           GetModelByIdUseCase getModelByIdUseCase) {
        this.getModelCatalogUseCase = getModelCatalogUseCase;
        this.getModelByIdUseCase = getModelByIdUseCase;
    }

    @GetMapping
    public List<ModelDto> list() {
        return getModelCatalogUseCase.execute().stream()
                .map(ModelDto::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDto> getById(@PathVariable String id) {
        return getModelByIdUseCase.execute(id)
                .map(ModelDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
