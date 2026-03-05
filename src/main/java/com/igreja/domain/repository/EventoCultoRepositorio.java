package com.igreja.domain.repository;

import com.igreja.domain.model.EventoCulto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventoCultoRepositorio {
    Optional<EventoCulto> findById(UUID id);
    List<EventoCulto> findAll();
    void salvar(EventoCulto eventoCulto);
}

