package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorUsuariosTest {


    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        Usuario user = new Usuario(null, "Nuevo Cliente para borrar", "m@n.com", LocalDate.now(), true);

        controladorUsuarios.alta(user);

        System.out.println(user);

        assertThat(user.getId(), greaterThan(0));

    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() {
        assertThrows(UsuarioException.class, () -> {
            Usuario user = new Usuario(null, "Nuevo Cliente 16", "mn.com", LocalDate.now(), true);
            controladorUsuarios.alta(user);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
        Usuario user = new Usuario(11, "Nuevo Cliente 11", "mmmmm@n.com", LocalDate.now(), true);

        Usuario userUpdate = controladorUsuarios.actualizar(user);

        System.out.println(user);

        assertThat(user.getEmail(), is(userUpdate.getEmail()));
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        assertThrows(UsuarioException.class, () -> {
            Usuario user = new Usuario(13, "Nuevo Cliente 2", "mn.com", LocalDate.now(), true);
            controladorUsuarios.actualizar(user);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() {
        Usuario user = new Usuario();

        user.setId(20);

        boolean ok = controladorUsuarios.baja(user);

        assertThat(ok, is(true));
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
        Usuario user = new Usuario();
        user.setId(80);
        assertThrows(UsuarioException.class, () -> {
            boolean ok = controladorUsuarios.baja(user);
        });
    }
}