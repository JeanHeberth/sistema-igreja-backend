package com.igreja.application.service;

import com.igreja.adapters.web.support.factory.DomainTestFactory;
import com.igreja.domain.model.Voto;
import com.igreja.domain.repository.VotoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VotacaoServiceTest {

    private VotoRepositorio votoRepositorio;
    private VotacaoService votacaoService;

    @BeforeEach
    void setUp() {
        votoRepositorio = mock(VotoRepositorio.class);
        votacaoService = new VotacaoService(votoRepositorio);
    }

    @Test
    void registrarVoto_deveSalvarQuandoAindaNaoVotou() {
        UUID usuarioId = UUID.randomUUID();
        UUID hinoId = UUID.randomUUID();
        UUID cultoId = UUID.randomUUID();
        Voto voto = DomainTestFactory.novoVoto(hinoId, usuarioId, cultoId);

        when(votoRepositorio.existePorUsuarioIdEHinoIdECultoId(usuarioId, hinoId, cultoId))
                .thenReturn(false);

        Voto resultado = votacaoService.registrarVoto(voto);

        assertSame(voto, resultado);
        verify(votoRepositorio, times(1)).salvar(voto);
    }

    @Test
    void registrarVoto_deveLancarExcecaoQuandoJaVotou() {
        UUID usuarioId = UUID.randomUUID();
        UUID hinoId = UUID.randomUUID();
        UUID cultoId = UUID.randomUUID();
        Voto voto = DomainTestFactory.novoVoto(hinoId, usuarioId, cultoId);

        when(votoRepositorio.existePorUsuarioIdEHinoIdECultoId(usuarioId, hinoId, cultoId))
                .thenReturn(true);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> votacaoService.registrarVoto(voto)
        );

        assertEquals("Usuário já votou neste hino para este culto.", ex.getMessage());
        verify(votoRepositorio, never()).salvar(any());
    }

    @Test
    void buscarPorId_deveDelegarParaRepositorio() {
        UUID id = UUID.randomUUID();
        Voto voto = mock(Voto.class);
        when(votoRepositorio.findById(id)).thenReturn(Optional.of(voto));

        Optional<Voto> resultado = votacaoService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertSame(voto, resultado.get());
        verify(votoRepositorio, times(1)).findById(id);
    }

    @Test
    void listarPorCulto_deveDelegarParaRepositorio() {
        UUID cultoId = UUID.randomUUID();
        List<Voto> lista = List.of(mock(Voto.class), mock(Voto.class));
        when(votoRepositorio.findByCultoId(cultoId)).thenReturn(lista);

        List<Voto> resultado = votacaoService.listarPorCulto(cultoId);

        assertEquals(lista, resultado);
        verify(votoRepositorio, times(1)).findByCultoId(cultoId);
    }

    @Test
    void listarPorHino_deveDelegarParaRepositorio() {
        UUID hinoId = UUID.randomUUID();
        List<Voto> lista = List.of(mock(Voto.class));
        when(votoRepositorio.findByHinoId(hinoId)).thenReturn(lista);

        List<Voto> resultado = votacaoService.listarPorHino(hinoId);

        assertEquals(lista, resultado);
        verify(votoRepositorio, times(1)).findByHinoId(hinoId);
    }
}

