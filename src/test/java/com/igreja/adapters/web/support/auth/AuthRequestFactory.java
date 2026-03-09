package com.igreja.adapters.web.support.auth;

import java.util.Map;

public final class AuthRequestFactory {

    private AuthRequestFactory() {
    }

    public static Map<String, Object> loginValidoAdmin() {
        return Map.of(
                "email", AuthTestConstants.ADMIN_EMAIL,
                "senha", AuthTestConstants.ADMIN_SENHA
        );
    }

    public static Map<String, Object> loginInvalidoAdmin() {
        return Map.of(
                "email", AuthTestConstants.ADMIN_EMAIL,
                "senha", AuthTestConstants.SENHA_INVALIDA
        );
    }
}
