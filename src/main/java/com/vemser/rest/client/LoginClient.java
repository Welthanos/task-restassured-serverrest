package com.vemser.rest.client;

import com.vemser.rest.model.Login;
import com.vemser.rest.specs.LoginSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginClient {
    public LoginClient() {}

    public Response fazerLogin(Login login) {

        return
            given()
                .spec(LoginSpecs.loginReqSpec())
                .body(login)
                .post("/login")
        ;
    }

    public Response fazerLoginSemCorpo() {

        return
            given()
                .spec(LoginSpecs.loginReqSpec())
                .post("/login")
        ;
    }
}
