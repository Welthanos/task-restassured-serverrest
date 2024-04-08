package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.Produto;
import com.vemser.rest.specs.ProdutoSpecs;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ProdutoFuncionalTest {
    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testBuscarProdutosComSucesso() {

        produtoClient.buscarProdutos()
            .then()
                .spec(ProdutoSpecs.produtoResSpec(200))
                .body("$", allOf(
                        hasKey("quantidade"),
                        hasKey("produtos")
                ))
        ;
    }

    @Test
    public void testBuscarProdutoPorNomeComSucesso() {
        Produto produto = ProdutoDataFactory.criarProduto();

        produtoClient.buscarProdutoPorNome(produto)
            .then()
                .spec(ProdutoSpecs.produtoResSpec(200))
                .body("quantidade", equalTo(1), "produtos[0].nome", equalTo(produto.getNome()))
        ;
    }

    @Test
    public void testBuscarProdutoPorIdComSucesso() {

        produtoClient.buscarProdutoPorId(ProdutoDataFactory.criarProduto())
            .then()
                .spec(ProdutoSpecs.produtoResSpec(200))
                .body("$", allOf(
                        hasKey("_id"),
                        hasKey("nome"),
                        hasKey("preco"),
                        hasKey("descricao"),
                        hasKey("quantidade")
                ))
        ;
    }

    @Test
    public void testCadastrarProdutoComSucesso() {

        produtoClient.cadastrarProduto(ProdutoDataFactory.criarProduto())
            .then()
                .spec(ProdutoSpecs.produtoResSpec(201))
                .body("message", equalTo("Cadastro realizado com sucesso"), "_id", notNullValue())
        ;
    }

    @Test
    public void testTentarCadastrarProdutoComNomeEmBranco() {

        produtoClient.cadastrarProduto(ProdutoDataFactory.criarProdutoComNomeEmBranco())
            .then()
                .spec(ProdutoSpecs.produtoResSpec(400))
                .body("nome", equalTo("nome não pode ficar em branco"))
        ;
    }

    @Test
    public void testTentarCadastrarProdutoComPrecoZero() {

        produtoClient.cadastrarProduto(ProdutoDataFactory.criarProdutoComPrecoZero())
            .then()
                .spec(ProdutoSpecs.produtoResSpec(400))
                .body("preco", equalTo("preco deve ser um número positivo"))
        ;
    }

    @Test
    public void testTentarCadastrarProdutoSemCorpo() {

        produtoClient.cadastrarProdutoSemCorpo()
            .then()
                .spec(ProdutoSpecs.produtoResSpec(400))
                .body(
                        "nome", equalTo("nome é obrigatório"),
                        "preco", equalTo("preco é obrigatório"),
                        "descricao", equalTo("descricao é obrigatório"),
                        "quantidade", equalTo("quantidade é obrigatório")
                )
        ;
    }

    @Test
    public void testAtualizarProdutoComSucesso() {

        produtoClient.atualizarProduto(ProdutoDataFactory.criarProduto())
            .then()
                .spec(ProdutoSpecs.produtoResSpec(200))
                .body("message", equalTo("Registro alterado com sucesso"))
        ;
    }

    @Test
    public void testExcluirProdutoPorIdComSucesso() {
        produtoClient.excluirProdutoPorId(ProdutoDataFactory.criarProduto())
            .then()
                .spec(ProdutoSpecs.produtoResSpec(200))
                .body("message", equalTo("Registro excluído com sucesso"))
        ;
    }

    @Test
    public void testTentarExcluirProdutoPorIdInexistente() {

        produtoClient.excluirProdutoPorIdInexistente(ProdutoDataFactory.criarProduto())
            .then()
                .spec(ProdutoSpecs.produtoResSpec(200))
                .body("message", equalTo("Nenhum registro excluído"))
        ;
    }
}