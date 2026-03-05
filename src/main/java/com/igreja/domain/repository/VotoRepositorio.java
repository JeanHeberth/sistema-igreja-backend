package com.igreja.domain.repository;

import com.igreja.domain.model.Voto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VotoRepositorio {
    Optional<Voto> findById(UUID id);
    List<Voto> findByCultoId(UUID cultoId);
    List<Voto> findByHinoId(UUID hinoId);
    boolean existePorUsuarioIdEHinoIdECultoId(UUID usuarioId, UUID hinoId, UUID cultoId);
    void salvar(Voto voto);
}

