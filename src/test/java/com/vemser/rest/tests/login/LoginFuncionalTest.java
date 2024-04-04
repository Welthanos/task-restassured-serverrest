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
}
