package com.igreja.adapters.web.resource;

import com.igreja.adapters.web.record.request.UsuarioRequest;

import com.igreja.adapters.web.support.auth.AuthTestDataSupport;
import com.igreja.adapters.web.support.base.BaseIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;


import static com.igreja.adapters.web.support.auth.AuthTokenSupport.obterTokenAdmin;
import static com.igreja.adapters.web.support.factory.UsuarioRequestFactory.*;
import static io.restassured.RestAssured.given;

@QuarkusTest
class UsuarioResourceTest extends BaseIntegrationTest {


    @Inject
    AuthTestDataSupport authTestDataSupport;

    @BeforeEach
    void setUp() {
        authTestDataSupport.garantirAdminPadrao();
    }

    @Test
    @DisplayName("Criar usuário deve retornar 201 e permitir busca por ID")
    void criarUsuario_deveRetornar201EPermitirBuscaPorId() {
        String token = obterTokenAdmin();

        UsuarioRequest request = novoUsuarioRequestComEmail("usuario.teste@example.com");

        String id = criarUsuario(token, request)
                .statusCode(201)
                .header("Location", Matchers.containsString("/usuarios/"))
                .body("email", Matchers.equalTo("usuario.teste@example.com"))
                .extract()
                .path("id");

        buscarPorId(token, id)
                .statusCode(200)
                .body("id", Matchers.equalTo(id))
                .body("email", Matchers.equalTo("usuario.teste@example.com"));
    }

    @Test
    @DisplayName("Buscar usuário inexistente deve retornar 404")
    void buscarPorId_quandoNaoExiste_deveRetornar404() {
        String token = obterTokenAdmin();

        buscarPorId(token, UUID.randomUUID().toString())
                .statusCode(404);
    }

    @Test
    @DisplayName("Criar usuário sem token deve retornar 401")
    void criarUsuario_semToken_deveRetornar401() {
        UsuarioRequest request = novoUsuarioRequestPadrao();

        criarUsuarioSemToken(request)
                .statusCode(401);
    }

    @Test
    @DisplayName("Buscar usuário sem token deve retornar 401")
    void buscarPorId_semToken_deveRetornar401() {
       buscarPorIdSemToken(UUID.randomUUID().toString())
                .statusCode(401);
    }

    @Test
    @DisplayName("Usuário criado deve conseguir realizar login")
    void usuarioCriado_deveConseguirRealizarLogin() {
        String adminToken = obterTokenAdmin();
        String email = "novo.usuario.login@example.com";

        UsuarioRequest request = novoUsuarioRequestComEmail(email);

        criarUsuario(adminToken, request)
                .statusCode(201);

        given()
                .contentType(io.restassured.http.ContentType.JSON)
                .body("""
                        {
                          "email": "%s",
                          "senha": "senha123"
                        }
                        """.formatted(email))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Criar usuário com email duplicado deve retornar 409 e mensagem de erro")
    void criarUsuario_comEmailDuplicado_deveRetornar409() {

        String token = obterTokenAdmin();
        String email = "duplicado@example.com";

        UsuarioRequest request = novoUsuarioRequestComEmail(email);

        criarUsuario(token, request)
                .statusCode(201);

        criarUsuario(token, request)
                .statusCode(409)
                .body("message", Matchers.containsString("Email já cadastrado"));
    }

    @Test
    @DisplayName("Criar usuário com payload inválido deve retornar 400")
    void criarUsuario_comPayloadInvalido_deveRetornar400() {
        String token = obterTokenAdmin();

        UsuarioRequest request = new UsuarioRequest(
                "",
                null,
                "",
                Set.of(),
                null
        );

        criarUsuario(token, request)
                .statusCode(400);
    }

    @Test
    @DisplayName("Criar usuário com papel inválido deve retornar 400")
    void criarUsuario_comPapelInvalido_deveRetornar400() {
        String token = obterTokenAdmin();

        UsuarioRequest request = new UsuarioRequest(
                "Usuario Papel Invalido",
                "papel.invalido@example.com",
                "senha123",
                Set.of("SUPER_ADMIN"),
                UUID.randomUUID()
        );

        criarUsuario(token, request)
                .statusCode(400)
                .body("message", Matchers.containsString("Papel"));
    }

    @Test
    @DisplayName("Criar usuário com perfil sem permissão deve retornar 403")
    void criarUsuario_comPerfilSemPermissao_deveRetornar403() {
        String adminToken = obterTokenAdmin();

        UsuarioRequest membroRequest = novoUsuarioRequestComEmail("membro.sem.permissao@example.com");

        criarUsuario(adminToken, membroRequest)
                .statusCode(201);

        String tokenMembro = given()
                .contentType(io.restassured.http.ContentType.JSON)
                .body("""
                    {
                      "email": "membro.sem.permissao@example.com",
                      "senha": "senha123"
                    }
                    """)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        UsuarioRequest novoUsuario = novoUsuarioRequestComEmail("novo.usuario@example.com");

        criarUsuario(tokenMembro, novoUsuario)
                .statusCode(403);
    }
}