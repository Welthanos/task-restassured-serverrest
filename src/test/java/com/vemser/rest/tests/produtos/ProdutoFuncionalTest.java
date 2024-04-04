package com.vemser.rest.tests.produtos;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class ProdutoFuncionalTest {
    private static String token;

    @BeforeAll
    public static void setUp() {
        baseURI = "http://localhost:3000";
    }

    @BeforeEach
    public void criarToken() {
        Response response =
                given()
                        .basePath("/login")
                        .header("Content-Type", "application/json")
                        .body(
                                """
                                          {
                                               "email": "thanos.lago@gmail.com",
                                               "password": "ltYms34QDHo1zvXC"
                                          }
                                   """
                        )
                        .post();

        token = response.jsonPath().getString("authorization");
    }

    @Test
    public void testBuscarProdutosComSucesso() {
        given()
        .when()
                .get("/produtos")
        .then()
            .assertThat()
                .statusCode(200);
    }

    @Test
    public void testBuscarProdutoPorIdComSucesso() {
        String id = "BJWCtEasByCQsrEJ";

        given()
                .pathParam("_id", id)
        .when()
                .get("/produtos/{_id}")
        .then()
            .assertThat()
                .statusCode(200);
    }

    @Test
    public void testCadastrarProdutoComSucesso() {

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(
                        """
                                 {
                                      "nome": "Notebook ASUS",
                                      "preco": 4550,
                                      "descricao": "Notebook ASUS 16GB RAM 1TB SSD Windows 11",
                                      "quantidade": 350
                                 }
                           """
                )
        .when()
                .post("/produtos")
        .then()
            .assertThat()
                .statusCode(201)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    public void testAtualizarProdutoComSucesso() {
        String id = "e5yGEoEHS4snRpGB";

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .pathParam("_id", id)
                .body(
                        """
                                 {
                                      "nome": "Notebook Dell",
                                      "preco": 4550,
                                      "descricao": "Notebook Dell 16GB RAM 1TB SSD Windows 11",
                                      "quantidade": 350
                                 }
                           """
                )
        .when()
                .put("/produtos/{_id}")
        .then()
            .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    public void testExcluirProdutoPorIdComSucesso() {
        String id = "inGSJO9uyFqeIIZN";

        given()
                .header("Authorization", token)
                .pathParam("_id", id)
        .when()
                .delete("/produtos/{_id}")
        .then()
            .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Registro excluído com sucesso"));
    }
}