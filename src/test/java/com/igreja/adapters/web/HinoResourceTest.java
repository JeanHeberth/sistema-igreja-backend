package com.igreja.adapters.web;

import com.igreja.adapters.web.dto.HinoRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
class HinoResourceTest {

    @Test
    void criarHino_deveRetornar201EPermitirBuscaPorId() {
        UUID coralId = UUID.randomUUID();
        UUID cultoId = UUID.randomUUID();
        UUID submetidoPor = UUID.randomUUID();

        HinoRequest request = new HinoRequest();
        request.setTitulo("Hino Teste");
        request.setAutor("Autor Teste");
        request.setLetra("Letra de teste");
        request.setUrlMidia("https://midia.example.com/hino.mp3");
        request.setCoralId(coralId);
        request.setCultoId(cultoId);
        request.setSubmetidoPor(submetidoPor);

        String id =
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                .when()
                        .post("/hinos")
                .then()
                        .statusCode(201)
                        .header("Location", Matchers.containsString("/hinos/"))
                        .body("titulo", Matchers.equalTo("Hino Teste"))
                        .extract()
                        .path("id");

        given()
        .when()
                .get("/hinos/" + id)
        .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(id))
                .body("titulo", Matchers.equalTo("Hino Teste"));
    }

    @Test
    void buscarPorId_quandoNaoExiste_deveRetornar404() {
        UUID id = UUID.randomUUID();

        given()
        .when()
                .get("/hinos/" + id)
        .then()
                .statusCode(404);
    }
}

