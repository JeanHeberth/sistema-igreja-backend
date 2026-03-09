package com.igreja.application.service;

import com.igreja.domain.model.Voto;
import com.igreja.domain.repository.VotoRepositorio;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de aplicação para operações de votação.
 */
@ApplicationScoped
public class VotacaoService {

    private final VotoRepositorio votoRepositorio;

    public VotacaoService(VotoRepositorio votoRepositorio) {
        this.votoRepositorio = votoRepositorio;
    }

    public Voto registrarVoto(Voto voto) {
        boolean jaVotou = votoRepositorio.existePorUsuarioIdEHinoIdECultoId(
                voto.getUsuarioId(), voto.getHinoId(), voto.getCultoId()
        );
        if (jaVotou) {
            throw new IllegalStateException("Usuário já votou neste hino para este culto.");
        }
        votoRepositorio.salvar(voto);
        return voto;
    }

    public Optional<Voto> buscarPorId(UUID id) {
        return votoRepositorio.findById(id);
    }

    public List<Voto> listarPorCulto(UUID cultoId) {
        return votoRepositorio.findByCultoId(cultoId);
    }

    public List<Voto> listarPorHino(UUID hinoId) {
        return votoRepositorio.findByHinoId(hinoId);
    }
}
