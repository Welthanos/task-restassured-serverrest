package com.vemser.rest.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class LoginSpecs {
    private LoginSpecs() {}

    public static RequestSpecification loginReqSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setUp())
                .setContentType(ContentType.JSON)
                .build()
        ;
    }

    public static ResponseSpecification loginResSpec(Integer statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectHeader("Content-type", "application/json; charset=utf-8")
                .build()
        ;
    }
}
