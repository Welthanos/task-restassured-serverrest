package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.specs.LoginSpecs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginFuncionalTest {
    private LoginClient loginClient = new LoginClient();

    @BeforeEach
    public void setUp() {
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testLoginComSucesso() {

        loginClient.fazerLogin(LoginDataFactory.criarLogin())
            .then()
                .spec(LoginSpecs.loginResSpec(200))
                .body("message", equalTo("Login realizado com sucesso"), "authorization", notNullValue());
    }

    @Test
    public void testLoginInvalidoComEmailEmBranco() {

        loginClient.fazerLogin(LoginDataFactory.criarLoginComEmailEmBranco())
        .then()
                .spec(LoginSpecs.loginResSpec(400))
                .body("email", equalTo("email não pode ficar em branco"));
    }

    @Test
    public void testLoginInvalidoComSenhaEmBranco() {

        loginClient.fazerLogin(LoginDataFactory.criarLoginComSenhaEmBranco())
            .then()
                .spec(LoginSpecs.loginResSpec(400))
                .body("password", equalTo("password não pode ficar em branco"));
    }

    @Test
    public void testLoginInvalidoComEmailNaoCadastrado() {

        loginClient.fazerLogin(LoginDataFactory.criarLoginComEmailNaoCadastrado())
            .then()
                .spec(LoginSpecs.loginResSpec(401))
                .body("message", equalTo("Email e/ou senha inválidos"));
    }

    @Test
    public void testLoginInvalidoComEmailValidoESenhaIncorreta() {

        loginClient.fazerLogin(LoginDataFactory.criarLoginComEmailValidoESenhaIncorreta())
            .then()
                .spec(LoginSpecs.loginResSpec(401))
                .body("message", equalTo("Email e/ou senha inválidos"));
    }

    @Test
    public void testLoginInvalidoComCorpoVazio() {

        loginClient.fazerLoginSemCorpo()
            .then()
                .spec(LoginSpecs.loginResSpec(400))
                .body("email", equalTo("email é obrigatório"), "password", equalTo("password é obrigatório"))
        ;
    }
}
