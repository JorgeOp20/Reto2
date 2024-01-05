package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorMensajesTest {


    @Autowired
    ControladorMensajes controladorMensajes;

    @Test
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() {

        boolean mensaOK = controladorMensajes.enviarMensaje(11, 20, "Mensaje controller 2");

        assertTrue(mensaOK);
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() {

        assertThrows(Exception.class, () -> {
            boolean mensaOK = controladorMensajes.enviarMensaje(13, 11, "Hola a todos");
        });

    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() {

        boolean mensaOK = controladorMensajes.mostrarChat(11,12);

        assertTrue(mensaOK);

    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() {

        assertThrows(Exception.class, () -> {
            boolean mensaOK = controladorMensajes.mostrarChat(46, 11);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() {

        boolean borrarMsg = controladorMensajes.eliminarChatConUsuario(15, 16);

        assertThat(borrarMsg, is(true));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() {

        assertThrows(Exception.class, () -> {

            boolean borrarMsg = controladorMensajes.eliminarChatConUsuario(13, 16);

        });

    }
}