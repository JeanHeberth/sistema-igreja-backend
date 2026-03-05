package com.igreja.domain.repository;

import com.igreja.domain.model.Hino;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HinoRepositorio {
    Optional<Hino> findById(UUID id);
    List<Hino> findByCultoId(UUID cultoId);
    List<Hino> findByCoralId(UUID coralId);
    void salvar(Hino hino);
}

