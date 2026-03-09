package com.igreja.adapters.web;

import com.igreja.adapters.web.record.request.UsuarioRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import jakarta.enterprise.inject.Alternative;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import com.igreja.domain.repository.VotoRepositorio;
import com.igreja.domain.repository.EventoCultoRepositorio;

import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
@io.quarkus.test.junit.TestProfile(UsuarioResourceTest.MockProfile.class)
class UsuarioResourceTest {
    @Test
    void criarUsuario_deveRetornar201EPermitirBuscaPorId() {
        UUID coralId = UUID.randomUUID();

        UsuarioRequest request = new UsuarioRequest(
            "Usuario Teste",
            "usuario.teste@example.com",
            "senha123",
            Set.of("MEMBRO"),
            coralId
        );

        String id =
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                .when()
                        .post("/usuarios")
                .then()
                        .statusCode(201)
                        .header("Location", Matchers.containsString("/usuarios/"))
                        .body("email", Matchers.equalTo("usuario.teste@example.com"))
                        .extract()
                        .path("id");

        given()
        .when()
                .get("/usuarios/" + id)
        .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(id))
                .body("email", Matchers.equalTo("usuario.teste@example.com"));
    }

    @Test
    void buscarPorId_quandoNaoExiste_deveRetornar404() {
        UUID id = UUID.randomUUID();

        // Obter token válido
        String token = given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"admin@email.com\",\"senha\": \"admin123\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get("/usuarios/" + id)
        .then()
            .statusCode(404);
    }

    @Dependent
    public static class ProducesBeans {
        @jakarta.enterprise.inject.Produces
        @Alternative
        @Priority(1)
        VotoRepositorio votoRepositorioBean() {
            return new VotoRepositorioMock();
        }
        @jakarta.enterprise.inject.Produces
        @Alternative
        @Priority(1)
        EventoCultoRepositorio eventoCultoRepositorioBean() {
            return new EventoCultoRepositorioMock();
        }
    }

    public static class MockProfile implements io.quarkus.test.junit.QuarkusTestProfile {
        // Nenhuma configuração extra necessária
    }

    @Alternative
    @Priority(1)
    public static class VotoRepositorioMock implements VotoRepositorio {
        @Override
        public java.util.Optional<com.igreja.domain.model.Voto> findById(UUID id) {
            return java.util.Optional.empty();
        }
        @Override
        public java.util.List<com.igreja.domain.model.Voto> findByCultoId(UUID cultoId) {
            return java.util.Collections.emptyList();
        }
        @Override
        public java.util.List<com.igreja.domain.model.Voto> findByHinoId(UUID hinoId) {
            return java.util.Collections.emptyList();
        }
        @Override
        public void salvar(com.igreja.domain.model.Voto voto) {
            // mock: não faz nada
        }
        @Override
        public boolean existePorUsuarioIdEHinoIdECultoId(UUID usuarioId, UUID hinoId, UUID cultoId) {
            return false;
        }
        // Outros métodos obrigatórios podem ser adicionados aqui
    }

    @Alternative
    @Priority(1)
    public static class EventoCultoRepositorioMock implements EventoCultoRepositorio {
        @Override
        public java.util.Optional<com.igreja.domain.model.EventoCulto> findById(UUID id) {
            return java.util.Optional.empty();
        }
        @Override
        public java.util.List<com.igreja.domain.model.EventoCulto> findAll() {
            return java.util.Collections.emptyList();
        }
        @Override
        public void salvar(com.igreja.domain.model.EventoCulto eventoCulto) {
            // mock: não faz nada
        }
        // Outros métodos obrigatórios podem ser adicionados aqui
    }
}
