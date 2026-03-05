package com.igreja.adapters.web;

import com.igreja.adapters.web.dto.UsuarioRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
class UsuarioResourceTest {

    @Test
    void criarUsuario_deveRetornar201EPermitirBuscaPorId() {
        UUID coralId = UUID.randomUUID();

        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Usuário Teste");
        request.setEmail("usuario.teste@example.com");
        request.setSenha("senha123");
        request.setPapeis(Set.of("MEMBRO"));
        request.setCoralId(coralId);

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

        given()
        .when()
                .get("/usuarios/" + id)
        .then()
                .statusCode(404);
    }
}
