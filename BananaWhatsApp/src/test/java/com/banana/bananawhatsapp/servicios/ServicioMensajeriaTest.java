package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ServicioMensajeriaTest {

    @Autowired
    IServicioMensajeria servicio;

    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() {
        Usuario usuRemitente = new Usuario(15, null, null, null, true);
        Usuario usuDestinatario = new Usuario(18, null, null, null, true);
        //Mensaje mensa = new Mensaje(null, usuRemitente, usuDestinatario, "Hola Curso!!", LocalDate.now());

        Mensaje mensa = servicio.enviarMensaje(usuRemitente, usuDestinatario, "Hola de nuevo Curso");

        //  System.out.println(mensa);

        assertThat(mensa.getId(), greaterThan(0));
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() {

        assertThrows(MensajeException.class, () -> {
            Usuario usuRemitente = new Usuario(11, null, null, null, true);
            Usuario usuDestinatario = new Usuario(13, null, null, null, true);
            servicio.enviarMensaje(usuRemitente, usuDestinatario, null);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() {

        Usuario usuRemi = new Usuario();
        usuRemi.setId(11);

        Usuario usuDest = new Usuario();
        usuDest.setId(12);

        List<Mensaje> mensajeList = servicio.mostrarChatConUsuario(usuRemi, usuDest);

        System.out.println("Mensaje Retornado: " + mensajeList);

        assertThat(mensajeList.size(), greaterThan(0));

    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() {
        assertThrows(Exception.class, () -> {
            Usuario usuRemi = new Usuario();
            usuRemi.setId(11);

            Usuario usuDest = new Usuario();
            usuDest.setId(45);

            List<Mensaje> mensajeList = servicio.mostrarChatConUsuario(usuRemi,usuDest);

        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() {

        Usuario usuRemi = new Usuario();
        usuRemi.setId(15);

        Usuario usuDest = new Usuario();
        usuDest.setId(16);

        boolean borrarMsg = servicio.borrarChatConUsuario(usuRemi, usuDest);

        assertThat(borrarMsg, is(true));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() {
        assertThrows(Exception.class, () -> {
            Usuario usuRemi = new Usuario();
            usuRemi.setId(34);   //Usuario inactivo

            Usuario usuDest = new Usuario();
            usuDest.setId(16);

            boolean borrarMsg = servicio.borrarChatConUsuario(usuRemi, usuDest);

        });
    }
}