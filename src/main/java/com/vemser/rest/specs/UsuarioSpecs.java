package com.vemser.rest.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UsuarioSpecs {
    private UsuarioSpecs() {}

    public static RequestSpecification usuarioReqSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setUp())
                .setContentType(ContentType.JSON)
                .build()
        ;
    }

    public static ResponseSpecification usuarioResSpec(Integer statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectHeader("Content-type", "application/json; charset=utf-8")
                .build();
    }
}
