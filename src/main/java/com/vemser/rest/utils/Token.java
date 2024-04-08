package com.vemser.rest.utils;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import org.junit.jupiter.api.BeforeAll;

public class Token {
    private static LoginClient loginClient = new LoginClient();

    @BeforeAll
    public static String tokenValido() {
        return loginClient.fazerLogin(LoginDataFactory.criarLogin()).jsonPath().getString("authorization");
    }
}
