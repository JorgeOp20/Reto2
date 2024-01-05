package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ServicioUsuariosTest {

    //IServicioUsuarios servicio;

    @Autowired
    IServicioUsuarios servUsuario;

    @Test
    void testBeans() {
        assertThat(servUsuario, notNullValue());
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrearUsuario_entoncesUsuarioValido() {
        Usuario user = new Usuario(null, "Nuevo Cliente para borrar", "m@n.com", LocalDate.now(), true);

        servUsuario.crearUsuario(user);

        System.out.println(user);

        assertThat(user.getId(), greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrearUsuario_entoncesExcepcion() {
        assertThrows(UsuarioException.class, () -> {
            Usuario user = new Usuario(null, "Nuevo Cliente 2", "mn.com", LocalDate.now(), true);
            servUsuario.crearUsuario(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarUsuario_entoncesUsuarioValido() {

        Usuario user = new Usuario();

        user.setId(18);

        boolean ok = servUsuario.borrarUsuario(user);

        assertThat(ok, is(true));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarUsuario_entoncesExcepcion() {
        Usuario user = new Usuario();
        user.setId(80);
        assertThrows(UsuarioException.class, () -> {
            boolean ok = servUsuario.borrarUsuario(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizarUsuario_entoncesUsuarioValido() {
        Usuario user = new Usuario(11, "Nuevo Cliente 11", "mmmmm@n.com", LocalDate.now(), true);

        Usuario userUpdate = servUsuario.actualizarUsuario(user);

        System.out.println(user);

        assertThat(user.getEmail(), is(userUpdate.getEmail()));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizarUsuario_entoncesExcepcion() {
        assertThrows(UsuarioException.class, () -> {
            Usuario user = new Usuario(13, "Nuevo Cliente 2", "mn.com", LocalDate.now(), true);
            servUsuario.actualizarUsuario(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDesinatarios_entoncesUsuarioValidos() {

        Set<Usuario> listaUsuarios = new HashSet<>();

        Usuario usuTest = new Usuario();
        usuTest.setId(11);

        listaUsuarios = servUsuario.obtenerPosiblesDesinatarios(usuTest, 3);

        System.out.println("Lista de usuarios: " + listaUsuarios);

        assertThat(listaUsuarios.size(), greaterThan(0));


    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDesinatarios_entoncesExcepcion() {


        assertThrows(UsuarioException.class, () -> {

            Usuario usuTest = new Usuario();
            usuTest.setId(55);

            Set<Usuario> listaUsuarios = new HashSet<>();
            listaUsuarios = servUsuario.obtenerPosiblesDesinatarios(usuTest, 3);
        });

    }
}