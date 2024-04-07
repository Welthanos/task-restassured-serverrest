package com.vemser.rest.tests.usuarios;
import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UsuarioFuncionalTest {
    private UsuarioClient usuarioClient = new UsuarioClient();
    private Faker faker = new Faker(new Locale("PT-BR"));

    @Test
    public void testBuscarUsuariosComSucesso() {

        usuarioClient.buscarUsuarios()
            .then()
                .assertThat()
                .statusCode(200).header("Content-type", "application/json; charset=utf-8")
        ;
    }

    @Test
    public void testBuscarUsuarioPorIdComSucesso() {
        Usuario usuario = UsuarioDataFactory.criarUsuario();

        usuarioClient.buscarUsuarioPorId(usuario)
            .then()
                .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("nome", equalTo(usuario.getNome()))
        ;
    }

    @Test
    public void testCadastrarUsuarioComSucesso() {

        usuarioClient.cadastrarUsuario(UsuarioDataFactory.criarUsuario())
            .then()
                .assertThat()
                .statusCode(201)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Cadastro realizado com sucesso"), "_id", notNullValue())
        ;
    }

    @Test
    public void testAtualizarUsuarioComSucesso() {

        usuarioClient.atualizarUsuario(UsuarioDataFactory.criarUsuario())
            .then()
                .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Registro alterado com sucesso"))
        ;
    }

    @Test
    public void testExcluirUsuarioPorIdComSucesso() {

        usuarioClient.excluirUsuarioPorId(UsuarioDataFactory.criarUsuario())
            .then()
                .assertThat()
                .statusCode(200)
                .header("Content-type", "application/json; charset=utf-8")
                .body("message", equalTo("Registro excluído com sucesso"));
    }
}
