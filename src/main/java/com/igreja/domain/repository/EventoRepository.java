package com.igreja.domain.repository;

import com.igreja.domain.model.Evento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventoRepository {
    Evento save(Evento evento);
    Optional<Evento> findById(UUID id);
    List<Evento> findAll();
    void deleteById(UUID id);
    Evento update(Evento evento);
}

