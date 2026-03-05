package com.igreja.application.service;

import com.igreja.domain.model.Usuario;
import com.igreja.domain.repository.UsuarioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepositorio usuarioRepositorio;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepositorio = mock(UsuarioRepositorio.class);
        usuarioService = new UsuarioService(usuarioRepositorio);
    }

    @Test
    void cadastrar_deveSalvarEDevolverUsuario() {
        Usuario usuario = DomainTestFactory.novoUsuarioDefault();

        Usuario resultado = usuarioService.cadastrar(usuario);

        assertSame(usuario, resultado);
        verify(usuarioRepositorio, times(1)).salvar(usuario);
    }

    @Test
    void buscarPorId_deveDelegarParaRepositorio() {
        UUID id = UUID.randomUUID();
        Usuario usuario = DomainTestFactory.novoUsuarioDefault();
        when(usuarioRepositorio.findById(id)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertSame(usuario, resultado.get());
        verify(usuarioRepositorio, times(1)).findById(id);
    }

    @Test
    void buscarPorEmail_deveDelegarParaRepositorio() {
        String email = "usuario.teste@example.com";
        Usuario usuario = DomainTestFactory.novoUsuarioComEmail(email);
        when(usuarioRepositorio.findByEmail(email)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorEmail(email);

        assertTrue(resultado.isPresent());
        assertSame(usuario, resultado.get());
        verify(usuarioRepositorio, times(1)).findByEmail(email);
    }
}

