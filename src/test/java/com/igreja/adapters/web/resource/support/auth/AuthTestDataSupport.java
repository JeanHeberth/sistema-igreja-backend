package com.igreja.adapters.web.resource.support.auth;

import com.igreja.adapters.web.resource.support.factory.DomainTestFactory;
import com.igreja.domain.model.Usuario;
import com.igreja.domain.repository.UsuarioRepositorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthTestDataSupport {

    @Inject
    UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Usuario garantirAdminPadrao() {
        return usuarioRepositorio.findByEmail(AuthTestConstants.ADMIN_EMAIL)
                .orElseGet(() -> {
                    Usuario admin = DomainTestFactory.novoAdminParaLogin();
                    usuarioRepositorio.salvar(admin);
                    return admin;
                });
    }
}