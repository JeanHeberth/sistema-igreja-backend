package com.igreja.application.service;

import com.igreja.domain.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.igreja.domain.repository.UsuarioRepositorio;
import io.smallrye.jwt.build.Jwt;
import java.util.Optional;
import java.util.Set;

import org.jboss.logging.Logger;

@ApplicationScoped
public class AuthService {

    @Inject
    UsuarioRepositorio usuarioRepositorio;

    private static final Logger LOG = Logger.getLogger(AuthService.class);

    public String authenticate(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(email); // Assume findByEmail retorna Optional<Usuario>
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String senhaUsuario = usuario.getSenha();
            String nomeUsuario = usuario.getNome();
            String idUsuario = usuario.getId() != null ? usuario.getId().toString() : null;
            // Obtenção dos papeis como lista de strings
            Set<String> papeis = usuario.getPapeis() != null ? usuario.getPapeis().stream().map(Enum::name).collect(java.util.stream.Collectors.toSet()) : java.util.Collections.emptySet();
            LOG.info("Papeis do usuário para JWT: " + papeis); // Log para depuração
            if (senha != null && senha.equals(senhaUsuario)) {
                LOG.info("Autenticação bem-sucedida para: " + email);
                return Jwt.issuer("sistema-igreja-api")
                        .subject(email)
                        .claim("id", idUsuario)
                        .claim("nome", nomeUsuario)
                        .claim("groups", papeis) // Corrigido para claim padrão do Quarkus
                        .sign();
            } else {
                LOG.warn("Senha incorreta para: " + email);
            }
        } else {
            LOG.warn("Usuário não encontrado: " + email);
        }
        return null;
    }
}
