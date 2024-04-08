package com.vemser.rest.data.factory;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.model.Login;
import com.vemser.rest.model.Usuario;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class LoginDataFactory {
    private static UsuarioClient usuarioClient = new UsuarioClient();
    private static Faker faker = new Faker(new Locale("PT-BR"));


    public static Login criarLogin() {
        Login login = new Login();
        Usuario usuario = UsuarioDataFactory.criarUsuarioAdmin();
        usuarioClient.cadastrarUsuario(usuario);
        login.setEmail(usuario.getEmail());
        login.setPassword(usuario.getPassword());

        return login;
    }

    public static Login criarLoginComEmailEmBranco() {
        Login login = criarLogin();
        login.setEmail(StringUtils.EMPTY);
        login.setPassword(login.getPassword());

        return login;
    }
    public static Login criarLoginComSenhaEmBranco() {
        Login login = criarLogin();
        login.setEmail(login.getEmail());
        login.setPassword(StringUtils.EMPTY);

        return login;
    }
    public static Login criarLoginComEmailNaoCadastrado() {
        Login login = criarLogin();
        login.setEmail(faker.internet().emailAddress());
        login.setPassword(login.getPassword());

        return login;
    }

    public static Login criarLoginComEmailValidoESenhaIncorreta() {
        Login login = criarLogin();
        login.setEmail(login.getEmail());
        login.setPassword(faker.internet().password());

        return login;
    }
}
