package com.vemser.rest.tests.usuarios;

import com.vemser.rest.tests.pojo.UsuarioPojo;
import com.vemser.rest.tests.pojo.UsuarioResponse;
import io.restassured.http.ContentType;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UsuarioFuncionalTest {
    private Faker faker = new Faker(new Locale("PT-BR"));

    @BeforeEach
    public void setUp() {
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testBuscarUsuariosComSucesso() {
        given()
        .when()
                .get("/usuarios")
        .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testBuscarUsuarioPorIdComSucesso() {
        String id = cadastrarUsuario().getId();

        given()
                .pathParam("_id", id)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testCadastrarUsuarioComSucesso() {
        UsuarioPojo usuario = novoUsuario();

        UsuarioResponse response =
                given()
                        .contentType(ContentType.JSON)
                        .body(usuario)
                .when()
                        .post("/usuarios")
                .then()
                        .assertThat()
                        .statusCode(201)
                        .header("Content-type", "application/json; charset=utf-8")
                        .extract().as(UsuarioResponse.class);

        Assertions.assertAll("response",
                () -> Assertions.assertEquals("Cadastro realizado com sucesso", response.getMessage()),
                () -> Assertions.assertNotNull(response.getId())
        );

        excluirUsuario(response.getId());
    }

    @Test
    public void testAtualizarUsuarioComSucesso() {
        UsuarioPojo usuarioAtualizado = novoUsuario();
        String id = cadastrarUsuario().getId();

        UsuarioResponse response =
                given()
                        .contentType(ContentType.JSON)
                        .pathParam("_id", id)
                        .body(usuarioAtualizado)
                .when()
                        .put("/usuarios/{_id}")
                .then()
                        .assertThat()
                        .statusCode(200)
                        .header("Content-type", "application/json; charset=utf-8")
                        .extract().as(UsuarioResponse.class);

        Assertions.assertEquals("Registro alterado com sucesso", response.getMessage());

        excluirUsuario(id);
    }

    @Test
    public void testExcluirUsuarioPorIdComSucesso() {
        String id = cadastrarUsuario().getId();

        given()
                .pathParam("_id", id)
        .when()
                .delete("/usuarios/{_id}")
        .then()
                .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    private UsuarioPojo novoUsuario() {
        UsuarioPojo usuario = new UsuarioPojo();

        usuario.setNome(faker.name().firstName() + " " + faker.name().lastName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setPassword(faker.internet().password());
        usuario.setAdministrador(String.valueOf(faker.bool().bool()));

        return usuario;
    }

    private UsuarioResponse cadastrarUsuario() {
        UsuarioPojo usuario = novoUsuario();

        return
                given()
                        .contentType(ContentType.JSON)
                        .body(usuario)
                .when()
                        .post("/usuarios")
                .then()
                        .extract().as(UsuarioResponse.class);
    }

    private void excluirUsuario(String id) {
        given()
                .pathParam("_id", id)
        .when()
                .delete("/usuarios/{_id}")
        .then();
    }
}
