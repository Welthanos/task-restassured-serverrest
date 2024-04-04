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
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("quantidade", equalTo(26));
    }

    @Test
    public void testBuscarProdutoPorNomeComSucesso() {
        String nome = "Pizza";

        given()
                .queryParam("nome", nome)
        .when()
                .get("/produtos")
        .then()
            .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("quantidade", equalTo(1))
                .body("produtos[0].nome", equalTo(nome));
    }

    @Test
    public void testBuscarProdutoPorIdComSucesso() {
        String id = "BJWCtEasByCQsrEJ";
        String nome = "Pizza";

        given()
                .pathParam("_id", id)
        .when()
                .get("/produtos/{_id}")
        .then()
            .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("nome", equalTo(nome));
    }

    @Test
    public void testTentarBuscarProdutoComIdInexistente() {
        String id = "BJWCtEasByCQ";

        given()
                .pathParam("_id", id)
        .when()
                .get("/produtos/{_id}")
        .then()
            .assertThat()
                .statusCode(400)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Produto não encontrado"));
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
    public void testCadastrarProdutoComNomeEmBranco() {

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(
                        """
                                 {
                                      "nome": "",
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
                .statusCode(400)
                .header("Content-type", "application/json; charset=utf-8")
                .body("nome", equalTo("nome não pode ficar em branco"));
    }

    @Test
    public void testTentarCadastrarProdutoPrecoZero() {

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(
                        """
                                 {
                                      "nome": "Notebook ASUS",
                                      "preco": 0,
                                      "descricao": "Notebook ASUS 16GB RAM 1TB SSD Windows 11",
                                      "quantidade": 350
                                 }
                           """
                )
        .when()
                .post("/produtos")
        .then()
            .assertThat()
                .statusCode(400)
                .header("Content-type", "application/json; charset=utf-8")
                .body("preco", equalTo("preco deve ser um número positivo"));
    }

    @Test
    public void testTentarCadastrarProdutoSemCorpo() {

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
        .when()
                .post("/produtos")
        .then()
            .assertThat()
                .statusCode(400)
                .header("Content-type", "application/json; charset=utf-8")
                .body("nome", equalTo("nome é obrigatório"))
                .body("preco", equalTo("preco é obrigatório"))
                .body("descricao", equalTo("descricao é obrigatório"))
                .body("quantidade", equalTo("quantidade é obrigatório"));
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
    public void testTentarAtualizarProdutoNomeJaCadastrado() {
        String id = "e5yGEoEHS4snRpGB";

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .pathParam("_id", id)
                .body(
                        """
                                 {
                                      "nome": "Pizza",
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
                .statusCode(400)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Já existe produto com esse nome"));
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

    @Test
    public void testTentarExcluirProdutoComIdInexistente() {
        String id = "inGSJO9uyFq";

        given()
                .header("Authorization", token)
                .pathParam("_id", id)
        .when()
                .delete("/produtos/{_id}")
        .then()
            .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Nenhum registro excluído"));
    }
}