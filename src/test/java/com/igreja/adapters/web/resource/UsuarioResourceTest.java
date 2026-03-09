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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.igreja.adapters.web.support.auth.AuthTokenSupport.obterTokenAdmin;
import static com.igreja.adapters.web.support.factory.UsuarioRequestFactory.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DisplayName("Erro deve retornar formato padronizado")
    void erro_deveRetornarFormatoPadronizado() {
        String token = obterTokenAdmin();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/usuarios/por-email")
                .then()
                .statusCode(400)
                .body("status", Matchers.equalTo(400))
                .body("error", Matchers.equalTo("Bad Request"))
                .body("message", Matchers.notNullValue())
                .body("path", Matchers.containsString("usuarios/por-email"));
    }

    @Test
    @DisplayName("Erro 400 deve retornar formato padronizado")
    void erro400_deveRetornarFormatoPadronizado() {

        String token = obterTokenAdmin();

        UsuarioRequest request = new UsuarioRequest(
                "Usuario Papel Invalido",
                "usuario.erro400@example.com",
                "senha123",
                java.util.Set.of("SUPER_ADMIN"),
                java.util.UUID.randomUUID()
        );

        criarUsuario(token, request)
                .statusCode(400)
                .body("status", Matchers.equalTo(400))
                .body("error", Matchers.equalTo("Bad Request"))
                .body("message", Matchers.notNullValue())
                .body("path", Matchers.containsString("usuarios"));
    }

    @Test
    @DisplayName("Erro 409 deve retornar formato padronizado")
    void erro409_deveRetornarFormatoPadronizado() {

        String token = obterTokenAdmin();
        String email = "usuario.duplicado.formato@example.com";

        UsuarioRequest request = novoUsuarioRequestComEmail(email);

        // primeiro cadastro
        criarUsuario(token, request)
                .statusCode(201);

        // segundo cadastro (gera conflito)
        criarUsuario(token, request)
                .statusCode(409)
                .body("status", Matchers.equalTo(409))
                .body("error", Matchers.equalTo("Conflict"))
                .body("message", Matchers.containsString("Email"))
                .body("path", Matchers.containsString("usuarios"));
    }

    @Test
    @DisplayName("Não deve permitir cadastro duplicado do mesmo email em concorrência")
    void naoDevePermitirCadastroDuplicadoEmConcorrencia() throws Exception {
        String token = obterTokenAdmin();
        String email = "concorrencia@example.com";

        int totalRequisicoes = 2;

        ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(totalRequisicoes);
        CountDownLatch pronto = new java.util.concurrent.CountDownLatch(totalRequisicoes);
        CountDownLatch iniciar = new java.util.concurrent.CountDownLatch(1);

        java.util.List<java.util.concurrent.Future<Integer>> futures = new java.util.ArrayList<>();

        for (int i = 0; i < totalRequisicoes; i++) {
            futures.add(executor.submit(() -> {
                UsuarioRequest request = novoUsuarioRequestComEmail(email);

                pronto.countDown();
                iniciar.await();

                return io.restassured.RestAssured.given()
                        .header("Authorization", "Bearer " + token)
                        .contentType(io.restassured.http.ContentType.JSON)
                        .body(request)
                        .when()
                        .post("/usuarios")
                        .then()
                        .extract()
                        .statusCode();
            }));
        }

        pronto.await();
        iniciar.countDown();

        List<Integer> resultados = new ArrayList<>();
        for (Future<Integer> future : futures) {
            resultados.add(future.get());
        }

        executor.shutdown();

        long sucesso201 = resultados.stream().filter(status -> status == 201).count();
        long conflito409 = resultados.stream().filter(status -> status == 409).count();

        assertEquals(1, sucesso201,
                "Deve haver exatamente um cadastro com sucesso");

        assertEquals(1, conflito409,
                "Deve haver exatamente um conflito por email duplicado");
    }

}