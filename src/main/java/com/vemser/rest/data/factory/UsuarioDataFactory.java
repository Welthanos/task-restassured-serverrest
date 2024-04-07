package com.vemser.rest.data.factory;
import com.vemser.rest.model.Usuario;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class UsuarioDataFactory {
    private static Faker faker = new Faker(new Locale("PT-BR"));

    public static Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome(faker.name().firstName() + " " + faker.name().lastName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setPassword(faker.internet().password());
        usuario.setAdministrador(String.valueOf(faker.bool().bool()));

        return usuario;
    }

    public static Usuario criarUsuarioAdmin() {
        Usuario usuario = criarUsuario();
        usuario.setAdministrador("true");

        return usuario;
    }

    public static Usuario criarUsuarioComNomeEmBranco() {
        Usuario usuario = criarUsuario();
        usuario.setNome(StringUtils.EMPTY);

        return usuario;
    }
}
