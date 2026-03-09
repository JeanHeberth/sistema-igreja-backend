package com.igreja.adapters.web.resource;

import com.igreja.adapters.web.record.request.HinoRequest;
import com.igreja.adapters.web.resource.support.auth.AuthTestDataSupport;
import com.igreja.adapters.web.resource.support.base.BaseIntegrationTest;
import com.igreja.adapters.web.resource.support.factory.HinoRequestFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.igreja.adapters.web.resource.support.auth.AuthTokenSupport.obterTokenAdmin;
import static com.igreja.adapters.web.resource.support.factory.HinoRequestFactory.*;
import static io.restassured.RestAssured.given;

@QuarkusTest
class HinoResourceTest extends BaseIntegrationTest {

   private static final String HINOS_PATH = "/hinos";

   @Inject
    AuthTestDataSupport authTestDataSupport;

   @BeforeEach
   void setUp() {
       authTestDataSupport.garantirAdminPadrao();
   }

    @Test
    @DisplayName("Criar hino deve retornar 201 e permitir busca por ID")
    void criarHino_deveRetornar201EPermitirBuscaPorId() {

       String token = obterTokenAdmin();

       HinoRequest request = valido();

        String id = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(HINOS_PATH)
                .then()
                .statusCode(201)
                .header("Location", Matchers.containsString("/hinos/"))
                .body("titulo", Matchers.equalTo("Hino Teste"))
                .extract()
                .path("id");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(HINOS_PATH + "/" + id)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(id))
                .body("titulo", Matchers.equalTo("Hino Teste"));

    }


    @Test
    void buscarPorId_quandoNaoExiste_deveRetornar404() {
        UUID id = UUID.randomUUID();

        String token = obterTokenAdmin();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(HINOS_PATH + "/" + id)
                .then()
                .statusCode(404);
    }
}
