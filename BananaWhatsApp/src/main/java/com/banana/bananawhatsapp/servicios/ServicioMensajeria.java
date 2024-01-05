package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import lombok.Setter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
public class ServicioMensajeria implements IServicioMensajeria{

    private IMensajeRepository repoMensajeria;
    private IUsuarioRepository repoUsuario;


    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException {
        Mensaje nMensa = new Mensaje(null, remitente, destinatario, texto, LocalDate.now());
        try {
            nMensa.valido();
            repoMensajeria.crear(nMensa);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nMensa;
    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {

        List<Mensaje> mensaFinal = new ArrayList<>();

        try {
            Set<Usuario> listUsuarios = repoUsuario.obtenerPosiblesDestinatarios(destinatario.getId(),100);
        } catch (SQLException e) {
            throw new UsuarioException(e.getMessage());
        }


        try {
            List<Mensaje> mensaRemi;

            mensaRemi = repoMensajeria.obtener(remitente);

            for (Mensaje iMensa:mensaRemi
                 ) {
                    if (iMensa.getRemitente().getId() == destinatario.getId()
                    || iMensa.getDestinatario().getId() == destinatario.getId())
                    mensaFinal.add(iMensa);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return mensaFinal;
    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {

        boolean borrarMsg = false;
        try {
            borrarMsg = repoMensajeria.borrarTodos(remitente, destinatario);
        } catch (SQLException e) {
            throw new MensajeException(e.getMessage());
        }

        return borrarMsg;
    }
}
