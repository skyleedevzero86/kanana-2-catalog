package com.sleekydz86.kanana.application.port;

import com.sleekydz86.kanana.domain.model.KananaModel;
import java.util.List;
import java.util.Optional;

public interface ModelRepositoryPort {

    List<KananaModel> findAll();

    Optional<KananaModel> findById(String id);
}
