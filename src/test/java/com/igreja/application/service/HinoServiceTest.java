package com.igreja.application.service;

import com.igreja.adapters.web.support.factory.DomainTestFactory;
import com.igreja.domain.model.Hino;
import com.igreja.domain.repository.HinoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HinoServiceTest {

    private HinoRepositorio hinoRepositorio;
    private HinoService hinoService;

    @BeforeEach
    void setUp() {
        hinoRepositorio = mock(HinoRepositorio.class);
        hinoService = new HinoService(hinoRepositorio);
    }

    @Test
    void cadastrar_deveSalvarEDevolverHino() {
        Hino hino = DomainTestFactory.novoHinoDefault();

        Hino resultado = hinoService.cadastrar(hino);

        assertSame(hino, resultado);
        verify(hinoRepositorio, times(1)).salvar(hino);
    }

    @Test
    void buscarPorId_deveDelegarParaRepositorio() {
        UUID id = UUID.randomUUID();
        Hino hino = DomainTestFactory.novoHinoDefault();
        when(hinoRepositorio.findById(id)).thenReturn(Optional.of(hino));

        Optional<Hino> resultado = hinoService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertSame(hino, resultado.get());
        verify(hinoRepositorio, times(1)).findById(id);
    }

    @Test
    void listarPorCoral_deveDelegarParaRepositorio() {
        UUID coralId = UUID.randomUUID();
        List<Hino> lista = List.of(DomainTestFactory.novoHinoDefault());
        when(hinoRepositorio.findByCoralId(coralId)).thenReturn(lista);

        List<Hino> resultado = hinoService.listarPorCoral(coralId);

        assertEquals(lista, resultado);
        verify(hinoRepositorio, times(1)).findByCoralId(coralId);
    }

    @Test
    void listarPorCulto_deveDelegarParaRepositorio() {
        UUID cultoId = UUID.randomUUID();
        List<Hino> lista = List.of(DomainTestFactory.novoHinoDefault(), DomainTestFactory.novoHinoDefault());
        when(hinoRepositorio.findByCultoId(cultoId)).thenReturn(lista);

        List<Hino> resultado = hinoService.listarPorCulto(cultoId);

        assertEquals(lista, resultado);
        verify(hinoRepositorio, times(1)).findByCultoId(cultoId);
    }
}

