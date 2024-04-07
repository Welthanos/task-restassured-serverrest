package com.vemser.rest.specs;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import static io.restassured.config.LogConfig.logConfig;

public class InicialSpecs {
    public static RequestSpecification setUp() {
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(3000)
                .setConfig(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).build()
        ;
    }
}
