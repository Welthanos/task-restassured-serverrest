package com.vemser.rest.client;

import com.vemser.rest.model.Produto;
import com.vemser.rest.specs.ProdutoSpecs;
import com.vemser.rest.utils.Token;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import static io.restassured.RestAssured.given;

public class ProdutoClient {
    public ProdutoClient() {}

    public Response buscarProdutos() {

        return
            given()
                .spec(ProdutoSpecs.produtoReqSpec())
            .when()
                .get("/produtos")
        ;
    }

    public Response buscarProdutoPorNome(Produto produto) {
        cadastrarProduto(produto);

        return
            given()
                .spec(ProdutoSpecs.produtoReqSpec())
                .queryParam("nome", produto.getNome())
            .when()
                .get("/produtos")
        ;
    }

    public Response buscarProdutoPorId(Produto produto) {
        String id = cadastrarProduto(produto).jsonPath().getString("_id");

        return
            given()
                .spec(ProdutoSpecs.produtoReqSpec())
                .pathParam("_id", id)
            .when()
                 .get("/produtos/{_id}")
        ;
    }

    public Response cadastrarProduto(Produto produto) {

        return
            given()
                .spec(ProdutoSpecs.produtoReqSpec())
                .header("Authorization", Token.tokenValido())
                .body(produto)
            .when()
                .post("/produtos")
        ;
    }

    public Response cadastrarProdutoSemCorpo() {

        return
            given()
                .spec(ProdutoSpecs.produtoReqSpec())
                .header("Authorization", Token.tokenValido())
            .when()
                .post("/produtos")
        ;
    }

    public Response atualizarProduto(Produto produto) {
        String id = cadastrarProduto(produto).jsonPath().getString("_id");

        return
            given()
                .spec(ProdutoSpecs.produtoReqSpec())
                .header("Authorization", Token.tokenValido())
                .pathParam("_id", id)
                .body(produto)
            .when()
                 .put("/produtos/{_id}")
        ;
    }

    public Response excluirProdutoPorId(Produto produto) {
        String id = cadastrarProduto(produto).jsonPath().getString("_id");

        return
            given()
                .spec(ProdutoSpecs.produtoReqSpec())
                .header("Authorization", Token.tokenValido())
                .pathParam("_id", id)
            .when()
                .delete("/produtos/{_id}")
        ;
    }

    public Response excluirProdutoPorIdInexistente(Produto produto) {
        String id = StringUtils.SPACE;

        return
            given()
                .spec(ProdutoSpecs.produtoReqSpec())
                .header("Authorization", Token.tokenValido())
                .pathParam("_id", id)
            .when()
                .delete("/produtos/{_id}")
        ;
    }
}
