package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class UsuarioRepositoryTest {
    //IUsuarioRepository repo;

    @Autowired
    IUsuarioRepository repo;

    @Test
    void testBeans() {
        assertThat(repo, notNullValue());
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws SQLException {
        Usuario user = new Usuario(null, "Nuevo Cliente 2", "m@n.com", LocalDate.now(), true);

        repo.crear(user);

        System.out.println(user);

        assertThat(user.getId(), greaterThan(0));

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() {
        assertThrows(UsuarioException.class, () -> {
            Usuario user = new Usuario(null, "Nuevo Cliente 8", "mn.com", LocalDate.now(), true);
            repo.crear(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws SQLException {
        Usuario user = new Usuario(11, "Nuevo Cliente 12", "mmm@n.com", LocalDate.now(), true);

        repo.actualizar(user);

        System.out.println(user);

        assertThat(user.getId(), greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        assertThrows(UsuarioException.class, () -> {
            Usuario user = new Usuario(13, "Nuevo Cliente 2", "mn.com", LocalDate.now(), true);
            repo.actualizar(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() throws SQLException {

        Usuario user = new Usuario(17, null, null, null, false);

        boolean ok = repo.borrar(user);

        assertThat(ok, is(true));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() throws SQLException {
        Usuario user = new Usuario(9, null, null, null, false);

        assertThrows(SQLException.class, () -> {
            boolean ok = repo.borrar(user);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDestinatarios_entoncesLista() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDestinatarios_entoncesExcepcion() {
    }

}