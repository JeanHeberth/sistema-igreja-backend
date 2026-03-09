package com.igreja.adapters.web.resource.support.factory;

import com.igreja.adapters.web.record.request.UsuarioRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class UsuarioRequestFactory {

    private static final String USUARIOS_PATH = "/usuarios";

    private UsuarioRequestFactory() {
    }

    public static UsuarioRequest novoUsuarioRequestPadrao() {
        return new UsuarioRequest(
                "Usuario Teste",
                "usuario." + UUID.randomUUID() + "@example.com",
                "senha123",
                Set.of("MEMBRO"),
                UUID.randomUUID()
        );
    }

    public static UsuarioRequest novoUsuarioRequestComEmail(String email) {
        return new UsuarioRequest(
                "Usuario Teste",
                email,
                "senha123",
                Set.of("MEMBRO"),
                UUID.randomUUID()
        );
    }

    public static UsuarioRequest usuarioComEmail(String email) {
        return new UsuarioRequest(
                "Usuario Teste",
                email,
                "senha123",
                Set.of("MEMBRO"),
                UUID.randomUUID()
        );
    }

    public static ValidatableResponse criarUsuario(String token, UsuarioRequest request) {
        return given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(USUARIOS_PATH)
                .then();
    }

    public static ValidatableResponse criarUsuarioSemToken(UsuarioRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(USUARIOS_PATH)
                .then();
    }

    public static ValidatableResponse buscarPorId(String token, String id) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(USUARIOS_PATH + "/" + id)
                .then();
    }

    public static ValidatableResponse buscarPorIdSemToken(String id) {
        return given()
                .when()
                .get(USUARIOS_PATH + "/" + id)
                .then();
    }
}