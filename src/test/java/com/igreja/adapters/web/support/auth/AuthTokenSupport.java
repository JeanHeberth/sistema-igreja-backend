package com.igreja.adapters.web.support.auth;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public final class AuthTokenSupport {

    private AuthTokenSupport() {
    }

    public static String obterTokenAdmin() {
        return given()
                .contentType(ContentType.JSON)
                .body(AuthRequestFactory.loginValidoAdmin())
                .when()
                .post(AuthTestConstants.AUTH_LOGIN_PATH)
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}