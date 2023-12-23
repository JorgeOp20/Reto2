package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class MensajeRepositoryTest {

//    IMensajeRepository repo;

    @Autowired
    IMensajeRepository repo;

    @Test
    void testBeans() {
        assertThat(repo, notNullValue());
    }

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws SQLException {

        Usuario usuRemitente = new Usuario(11,null, null, null, true);
        Usuario usuDestinatario = new Usuario(13, null, null, null, true);
        Mensaje mensa = new Mensaje(null, usuRemitente, usuDestinatario, "Hola Curso!!", LocalDate.now());

        repo.crear(mensa);

        System.out.println(mensa);

        assertThat(mensa.getId(), greaterThan(0));
    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() {
        assertThrows(SQLException.class, () -> {
            Mensaje mensa = new Mensaje(null, null, null, "Hola Curso!!", LocalDate.now());
            repo.crear(mensa);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() {
    }

}