package com.vemser.rest.client;

import com.vemser.rest.model.Usuario;
import com.vemser.rest.specs.UsuarioSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UsuarioClient {
    public UsuarioClient() {}

    public Response buscarUsuarios() {

        return
            given()
                .spec(UsuarioSpecs.usuarioReqSpec())
            .when()
                .get("/usuarios")
        ;
    }

    public Response buscarUsuarioPorId(Usuario usuario) {
        String id = cadastrarUsuario(usuario).then().extract().response().jsonPath().getString("_id");

        return
            given()
                .spec(UsuarioSpecs.usuarioReqSpec())
                .pathParam("_id", id)
            .when()
                .get("/usuarios/{_id}")
        ;
    }

    public Response cadastrarUsuario(Usuario usuario) {

        return
            given()
                 .spec(UsuarioSpecs.usuarioReqSpec())
                 .body(usuario)
            .when()
                 .post("/usuarios")
        ;
    }

    public Response atualizarUsuario(Usuario usuario) {
        String id = cadastrarUsuario(usuario).then().extract().response().jsonPath().getString("_id");

        return
            given()
                .spec(UsuarioSpecs.usuarioReqSpec())
                .pathParam("_id", id)
                .body(usuario)
            .when()
                .put("/usuarios/{_id}")
        ;
    }
    public Response excluirUsuarioPorId(Usuario usuario) {
        String id = cadastrarUsuario(usuario).then().extract().response().jsonPath().getString("_id");

        return
            given()
                .spec(UsuarioSpecs.usuarioReqSpec())
                .pathParam("_id", id)
            .when()
                 .delete("/usuarios/{_id}")
        ;
    }
}
