package com.vemser.rest.tests.usuarios;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UsuarioFuncionalTest {

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
        String id = "5ruot4a7kgxmn6pd";

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

        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                                {
                                    "nome": "Welthanos Del Lago",
                                    "email": "thanos.lago@gmail.com",
                                    "password": "ltYms34QDHo1zvXC",
                                    "administrador": "false"
                                }
                           """
                )
        .when()
                .post("/usuarios")
        .then()
            .assertThat()
                .statusCode(201)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    public void testAtualizarUsuarioComSucesso() {
        String id = "A9o3Lq8jiykBMRdX";

        given()
                .contentType(ContentType.JSON)
                .pathParam("_id", id)
                .body(
                        """
                                {
                                    "nome": "Welthanos Del Lagos Huecos",
                                    "email": "thanos.lago@gmail.com",
                                    "password": "ltYms34QDHo1zvXC",
                                    "administrador": "true"
                                }
                           """
                )
        .when()
                .put("/usuarios/{_id}")
        .then()
            .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    public void testExcluirUsuarioPorIdComSucesso() {
        String id = "1MMr8squdTGO83YP";

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
}
