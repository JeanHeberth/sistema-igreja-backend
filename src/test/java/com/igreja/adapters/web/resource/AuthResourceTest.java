package com.igreja.adapters.web.resource;

import com.igreja.adapters.web.resource.support.auth.AuthRequestFactory;
import com.igreja.adapters.web.resource.support.auth.AuthTestConstants;
import com.igreja.adapters.web.resource.support.auth.AuthTestDataSupport;
import com.igreja.adapters.web.resource.support.base.BaseIntegrationTest;
import com.igreja.adapters.web.resource.support.dataBase.TestDatabaseCleaner;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class AuthResourceTest extends BaseIntegrationTest {

    @Inject
    AuthTestDataSupport authTestDataSupport;

    @BeforeEach
    void setUp() {
        authTestDataSupport.garantirAdminPadrao();
    }

    @Test
    @DisplayName("Deve permitir login com credenciais válidas")
    void devePermitirLoginComCredenciaisValidas() {
        RestAssured.given()
                .contentType("application/json")
                .body(AuthRequestFactory.loginValidoAdmin())
                .when()
                .post(AuthTestConstants.AUTH_LOGIN_PATH)
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("Deve negar login com credenciais inválidas")
    void deveNegarLoginComCredenciaisInvalidas() {
        RestAssured.given()
                .contentType("application/json")
                .body(AuthRequestFactory.loginInvalidoAdmin())
                .when()
                .post(AuthTestConstants.AUTH_LOGIN_PATH)
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Deve permitir acesso com token válido")
    void devePermitirAcessoComTokenValido() {
        String token = RestAssured.given()
                .contentType("application/json")
                .body(AuthRequestFactory.loginValidoAdmin())
                .when()
                .post(AuthTestConstants.AUTH_LOGIN_PATH)
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(AuthTestConstants.USUARIOS_PATH)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Deve negar acesso sem token")
    void deveNegarAcessoSemToken() {
        RestAssured.given()
                .when()
                .get(AuthTestConstants.USUARIOS_PATH)
                .then()
                .statusCode(401);
    }
}