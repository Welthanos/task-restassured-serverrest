package com.vemser.rest.tests.usuarios;
import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.specs.UsuarioSpecs;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import java.util.Locale;

import static org.hamcrest.Matchers.*;

public class UsuarioFuncionalTest {
    private UsuarioClient usuarioClient = new UsuarioClient();
    private Faker faker = new Faker(new Locale("PT-BR"));

    @Test
    public void testBuscarUsuariosComSucesso() {

        usuarioClient.buscarUsuarios()
            .then()
                .spec(UsuarioSpecs.usuarioResSpec(200))
                .body("$", allOf(
                        hasKey("quantidade"),
                        hasKey("usuarios")
                ))
        ;
    }

    @Test
    public void testBuscarUsuarioPorIdComSucesso() {

        usuarioClient.buscarUsuarioPorId(UsuarioDataFactory.criarUsuario())
            .then()
                .spec(UsuarioSpecs.usuarioResSpec(200))
                .body("$", allOf(
                        hasKey("_id"),
                        hasKey("nome"),
                        hasKey("email"),
                        hasKey("password"),
                        hasKey("administrador")
                ))
        ;
    }

    @Test
    public void testCadastrarUsuarioComSucesso() {

        usuarioClient.cadastrarUsuario(UsuarioDataFactory.criarUsuario())
            .then()
                .spec(UsuarioSpecs.usuarioResSpec(201))
                .body("message", equalTo("Cadastro realizado com sucesso"), "_id", notNullValue())
        ;
    }

    @Test
    public void testAtualizarUsuarioComSucesso() {

        usuarioClient.atualizarUsuario(UsuarioDataFactory.criarUsuario())
            .then()
                .spec(UsuarioSpecs.usuarioResSpec(200))
                .body("message", equalTo("Registro alterado com sucesso"))
        ;
    }

    @Test
    public void testExcluirUsuarioPorIdComSucesso() {

        usuarioClient.excluirUsuarioPorId(UsuarioDataFactory.criarUsuario())
            .then()
                .spec(UsuarioSpecs.usuarioResSpec(200))
                .body("message", equalTo("Registro excluído com sucesso"))
        ;
    }
}
