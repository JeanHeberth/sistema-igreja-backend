package com.igreja.application.service;

import com.igreja.domain.model.Hino;
import com.igreja.domain.repository.HinoRepositorio;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de aplicação para operações com Hino.
 */
@ApplicationScoped
public class HinoService {

    private final HinoRepositorio hinoRepositorio;

    public HinoService(HinoRepositorio hinoRepositorio) {
        this.hinoRepositorio = hinoRepositorio;
    }

    public Hino cadastrar(Hino hino) {
        hinoRepositorio.salvar(hino);
        return hino;
    }

    public Optional<Hino> buscarPorId(UUID id) {
        return hinoRepositorio.findById(id);
    }

    public List<Hino> listarPorCoral(UUID coralId) {
        return hinoRepositorio.findByCoralId(coralId);
    }

    public List<Hino> listarPorCulto(UUID cultoId) {
        return hinoRepositorio.findByCultoId(cultoId);
    }
}
