package com.vemser.rest.tests.login;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginFuncionalTest {

    @BeforeEach
    public void setUp() {
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testLoginComSucesso() {

        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                                 {
                                      "email": "thanos.lago@gmail.com",
                                      "password": "ltYms34QDHo1zvXC"
                                 }
                           """
                )
        .when()
                .post("/login")
        .then()
            .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Login realizado com sucesso"))
                .body("authorization", notNullValue());
    }

    @Test
    public void testLoginInvalidoComEmailEmBranco() {

        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                                 {
                                      "email": "",
                                      "password": "ltYms34QDHo1zvXC"
                                 }
                           """
                )
        .when()
                .post("/login")
        .then()
            .assertThat()
                .statusCode(400)
                .header("Content-type", "application/json; charset=utf-8")
                .body("email", equalTo("email não pode ficar em branco"));
    }

    @Test
    public void testLoginInvalidoComSenhaEmBranco() {

        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                                 {
                                      "email": "thanos.lago@gmail.com",
                                      "password": ""
                                 }
                           """
                )
        .when()
                .post("/login")
        .then()
            .assertThat()
                .statusCode(400)
                .header("Content-type", "application/json; charset=utf-8")
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    public void testLoginInvalidoComEmailNaoCadastrado() {

        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                                 {
                                      "email": "thanos.lago@gmail.com.br",
                                      "password": "ltYms34QDHo1zvXC"
                                 }
                           """
                )
        .when()
                .post("/login")
        .then()
            .assertThat()
                .statusCode(401)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Email e/ou senha inválidos"));
    }

    @Test
    public void testLoginInvalidoComEmailValidoESenhaIncorreta() {

        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                                 {
                                      "email": "thanos.lago@gmail.com",
                                      "password": "ltYms34QDHo1zvXCas342"
                                 }
                           """
                )
        .when()
                .post("/login")
        .then()
            .assertThat()
                .statusCode(401)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Email e/ou senha inválidos"));
    }

    @Test
    public void testLoginInvalidoComCorpoVazio() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .post("/login")
        .then()
            .assertThat()
                .statusCode(400)
                .header("Content-type", "application/json; charset=utf-8")
                .body("email", equalTo("email é obrigatório"))
                .body("password", equalTo("password é obrigatório"));
    }
}
