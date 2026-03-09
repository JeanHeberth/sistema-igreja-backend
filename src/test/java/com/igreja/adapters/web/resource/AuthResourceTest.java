package com.igreja.adapters.web.resource;

import com.igreja.domain.enums.Papel;
import com.igreja.domain.model.Usuario;
import com.igreja.domain.repository.UsuarioRepositorio;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class AuthResourceTest {

    @Inject
    UsuarioRepositorio usuarioRepository;

    @BeforeEach
    @Transactional
    void setupAdminUser() {
        var existente = usuarioRepository.findByEmail("admin@email.com");

        if (existente.isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setId(UUID.randomUUID());
            usuario.setNome("Administrador");
            usuario.setEmail("admin@email.com");
            usuario.setSenha("admin123");
            usuario.setPapeis(Set.of(Papel.ADMIN));
            usuario.setCoralId(UUID.randomUUID());

            usuarioRepository.salvar(usuario);
        }
    }

    @Test
    void devePermitirLoginComCredenciaisValidas() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "email": "admin@email.com",
                          "senha": "admin123"
                        }
                        """)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void deveNegarLoginComCredenciaisInvalidas() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "email": "admin@email.com",
                          "senha": "errada"
                        }
                        """)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }

    @Test
    void devePermitirAcessoComTokenValido() {
        String token = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "email": "admin@email.com",
                          "senha": "admin123"
                        }
                        """)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/usuarios")
                .then()
                .statusCode(200);
    }

    @Test
    void deveNegarAcessoSemToken() {
        RestAssured.given()
                .when()
                .get("/usuarios")
                .then()
                .statusCode(401);
    }
}