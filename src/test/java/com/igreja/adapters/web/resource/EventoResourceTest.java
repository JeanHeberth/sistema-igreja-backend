package com.igreja.adapters.web.resource;

import com.igreja.adapters.web.record.request.EventoRequest;
import com.igreja.adapters.web.resource.support.factory.EventoRequestFactory;
import com.igreja.adapters.web.support.auth.AuthTestDataSupport;
import com.igreja.adapters.web.support.base.BaseIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.igreja.adapters.web.support.auth.AuthTokenSupport.obterTokenAdmin;
import static io.restassured.RestAssured.given;

@QuarkusTest
class EventoResourceTest extends BaseIntegrationTest {

    @Inject
    AuthTestDataSupport authTestDataSupport;

    @BeforeEach
    void setUp() {
        authTestDataSupport.garantirAdminPadrao();
    }

    @Test
    @DisplayName("Criar evento deve retornar 201 e permitir busca por ID")
    void criarEvento_deveRetornar201EPermitirBuscaPorId() {
        String token = obterTokenAdmin();
        EventoRequest request = EventoRequestFactory.novoEventoRequestComNome("Retiro Espiritual");

        String id = given()
                .header("Authorization", "Bearer " + token)
                .contentType(io.restassured.http.ContentType.JSON)
                .body(request)
                .when()
                .post("/eventos")
                .then()
                .statusCode(201)
                .header("Location", Matchers.containsString("/eventos/"))
                .body("nome", Matchers.equalTo("Retiro Espiritual"))
                .extract()
                .path("id");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/eventos/" + id)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(id))
                .body("nome", Matchers.equalTo("Retiro Espiritual"));
    }

    @Test
    @DisplayName("Buscar evento inexistente deve retornar 404")
    void buscarPorId_quandoNaoExiste_deveRetornar404() {
        String token = obterTokenAdmin();
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/eventos/" + UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Criar evento sem token deve retornar 401")
    void criarEvento_semToken_deveRetornar401() {
        EventoRequest request = EventoRequestFactory.novoEventoRequestPadrao();
        given()
                .contentType(io.restassured.http.ContentType.JSON)
                .body(request)
                .when()
                .post("/eventos")
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Listar eventos deve retornar 200 e lista")
    void listarEventos_deveRetornar200ELista() {
        String token = obterTokenAdmin();
        EventoRequest request = EventoRequestFactory.novoEventoRequestComNome("Encontro Jovem");
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(io.restassured.http.ContentType.JSON)
                .body(request)
                .when()
                .post("/eventos")
                .then()
                .statusCode(201);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/eventos")
                .then()
                .statusCode(200)
                .body("size()", Matchers.greaterThanOrEqualTo(1));
    }

    @Test
    @DisplayName("Atualizar evento deve retornar 200 e refletir alterações")
    void atualizarEvento_deveRetornar200ERefletirAlteracoes() {
        String token = obterTokenAdmin();
        EventoRequest request = EventoRequestFactory.novoEventoRequestComNome("Culto de Jovens");
        String id = given()
                .header("Authorization", "Bearer " + token)
                .contentType(io.restassured.http.ContentType.JSON)
                .body(request)
                .when()
                .post("/eventos")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        EventoRequest atualizado = EventoRequestFactory.novoEventoRequestComNome("Culto Atualizado");
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(io.restassured.http.ContentType.JSON)
                .body(atualizado)
                .when()
                .put("/eventos/" + id)
                .then()
                .statusCode(200)
                .body("nome", Matchers.equalTo("Culto Atualizado"));
    }

    @Test
    @DisplayName("Remover evento deve retornar 204 e não permitir busca")
    void removerEvento_deveRetornar204ENaoPermitirBusca() {
        String token = obterTokenAdmin();
        EventoRequest request = EventoRequestFactory.novoEventoRequestComNome("Evento Removido");
        String id = given()
                .header("Authorization", "Bearer " + token)
                .contentType(io.restassured.http.ContentType.JSON)
                .body(request)
                .when()
                .post("/eventos")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/eventos/" + id)
                .then()
                .statusCode(204);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/eventos/" + id)
                .then()
                .statusCode(404);
    }
}

