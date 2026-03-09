package com.igreja.application.service;

import com.igreja.domain.enums.CultoStatus;
import com.igreja.domain.enums.HinoStatus;
import com.igreja.domain.enums.Papel;
import com.igreja.domain.model.EventoCulto;
import com.igreja.domain.model.Hino;
import com.igreja.domain.model.Usuario;
import com.igreja.domain.model.Voto;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Fábrica utilitária para criação de objetos de domínio em testes.
 */
public final class DomainTestFactory {

    private DomainTestFactory() {
        // Classe utilitária
    }

    public static Usuario novoUsuarioDefault() {
        return new Usuario(
                UUID.randomUUID(),
                "Usuário Teste",
                "usuario.teste@example.com",
                "senha_hash_teste",
                Set.of(Papel.MEMBRO),
                UUID.randomUUID()
        );
    }

    public static Usuario novoUsuarioComEmail(String email) {
        return new Usuario(
                UUID.randomUUID(),
                "Usuário " + email,
                email,
                "senha_hash_teste",
                Set.of(Papel.MEMBRO),
                UUID.randomUUID()
        );
    }



    public static Hino novoHinoDefault() {
        UUID coralId = UUID.randomUUID();
        UUID cultoId = UUID.randomUUID();
        UUID submetidoPor = UUID.randomUUID();
        Instant agora = Instant.now();

        return new Hino(
                UUID.randomUUID(),
                "Título Teste",
                "Autor Teste",
                "Letra de teste",
                "https://midia.example.com/hino.mp3",
                submetidoPor,
                coralId,
                cultoId,
                HinoStatus.PENDENTE,
                0,
                agora,
                agora
        );
    }

    public static Hino novoHinoParaCulto(UUID cultoId, UUID coralId) {
        UUID submetidoPor = UUID.randomUUID();
        Instant agora = Instant.now();

        return new Hino(
                UUID.randomUUID(),
                "Hino Culto " + cultoId,
                "Autor Teste",
                "Letra de teste",
                "https://midia.example.com/hino.mp3",
                submetidoPor,
                coralId,
                cultoId,
                HinoStatus.PENDENTE,
                0,
                agora,
                agora
        );
    }

    public static EventoCulto novoEventoCultoDefault() {
        return new EventoCulto(
                UUID.randomUUID(),
                Instant.now().plusSeconds(3600),
                "Culto de Teste",
                CultoStatus.RASCUNHO,
                List.of()
        );
    }

    public static EventoCulto novoEventoCultoComHinos(List<UUID> hinosSelecionados) {
        return new EventoCulto(
            UUID.randomUUID(),
            Instant.now().plusSeconds(3600),
            "Culto com Hinos",
            CultoStatus.RASCUNHO,
            hinosSelecionados
        );
    }

    public static Voto novoVoto(UUID hinoId, UUID usuarioId, UUID cultoId) {
        return new Voto(
                UUID.randomUUID(),
                hinoId,
                usuarioId,
                cultoId,
                Instant.now()
        );
    }

    public static Voto novoVotoRandom() {
        UUID usuarioId = UUID.randomUUID();
        UUID hinoId = UUID.randomUUID();
        UUID cultoId = UUID.randomUUID();
        return novoVoto(hinoId, usuarioId, cultoId);
    }

    }

