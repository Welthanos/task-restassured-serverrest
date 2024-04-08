package com.vemser.rest.data.factory;

import com.vemser.rest.model.Produto;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Locale;

public class ProdutoDataFactory {
    private static Faker faker = new Faker(new Locale("PT-BR"));

    public static Produto criarProduto() {
        Produto produto = new Produto();
        produto.setNome(faker.book().title());
        produto.setPreco(faker.number().numberBetween(10, 10000));
        produto.setDescricao(faker.book().genre());
        produto.setQuantidade(faker.number().numberBetween(10, 1000));

        return produto;
    }

    public static Produto criarProdutoComNomeEmBranco() {
        Produto produto = criarProduto();
        produto.setNome(StringUtils.EMPTY);

        return produto;
    }

    public static Produto criarProdutoComPrecoZero() {
        Produto produto = criarProduto();
        produto.setPreco(NumberUtils.INTEGER_ZERO);

        return produto;
    }
}
