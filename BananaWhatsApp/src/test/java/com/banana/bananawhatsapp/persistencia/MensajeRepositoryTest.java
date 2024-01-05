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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
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

        Usuario usuRemitente = new Usuario(16, null, null, null, true);
        Usuario usuDestinatario = new Usuario(13, null, null, null, true);
        Mensaje mensa = new Mensaje(null, usuRemitente, usuDestinatario, "Mensaje Test 2!!!", LocalDate.now());

        repo.crear(mensa);

        System.out.println(mensa);

        assertThat(mensa.getId(), greaterThan(0));
    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() {
        assertThrows(Exception.class, () -> {
            Usuario usuRemitente = new Usuario(11, null, null, null, true);
            Usuario usuDestinatario = new Usuario(13, null, null, null, true);
            Mensaje mensa = new Mensaje(null, usuRemitente, usuDestinatario, "Hola Curso!!", LocalDate.now());
            repo.crear(mensa);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() throws SQLException {

        Usuario usuTest = new Usuario();
        usuTest.setId(11);

        List<Mensaje> mensajeList = repo.obtener(usuTest);

        System.out.println("Mensaje Retornado: " + mensajeList);

        assertThat(mensajeList.size(), greaterThan(0));

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {

        assertThrows(Exception.class, () -> {
            Usuario usuTest = new Usuario();
            usuTest.setId(46);

            List<Mensaje> mensajeList = repo.obtener(usuTest);

        });


    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() throws SQLException {


        Usuario usuRemi = new Usuario();
        usuRemi.setId(15);

        Usuario usuDest = new Usuario();
        usuDest.setId(16);

        boolean borrarMsg = repo.borrarTodos(usuRemi, usuDest);

        assertThat(borrarMsg, is(true));


    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() throws SQLException {

        assertThrows(Exception.class, () -> {
            Usuario usuRemi = new Usuario();
            usuRemi.setId(13);   //Usuario inactivo

            Usuario usuDest = new Usuario();
            usuDest.setId(16);

            boolean borrarMsg = repo.borrarTodos(usuRemi, usuDest);

        });

    }

}