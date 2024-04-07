package com.vemser.rest.tests.login;

import com.vemser.rest.tests.pojo.LoginPojo;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginFuncionalTest {

    @BeforeEach
    public void setUp() {
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testLoginComSucesso() {
        LoginPojo login = gerarLogin("thanos.lago@gmail.com", "ltYms34QDHo1zvXC");

        given()
                .contentType(ContentType.JSON)
                .body(login)
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
        LoginPojo login = gerarLogin("", "ltYms34QDHo1zvXC");

        given()
                .contentType(ContentType.JSON)
                .body(login)
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
        LoginPojo login = gerarLogin("thanos.lago@gmail.com", "");

        given()
                .contentType(ContentType.JSON)
                .body(login)
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
        LoginPojo login = gerarLogin("thanos.lago@gmail.com.br", "ltYms34QDHo1zvXC");

        given()
                .contentType(ContentType.JSON)
                .body(login)
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
        LoginPojo login = gerarLogin("thanos.lago@gmail.com", "ltYms34QDH45vxo1zvXCas342");

        given()
                .contentType(ContentType.JSON)
                .body(login)
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

    private LoginPojo gerarLogin(String email, String password) {
        LoginPojo login = new LoginPojo();

        login.setEmail(email);
        login.setPassword(password);

        return login;
    }
}
